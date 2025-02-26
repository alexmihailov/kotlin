/*
 * Copyright 2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include <cstdint>
#include <random>

#include "Cell.hpp"
#include "CustomAllocConstants.hpp"
#include "gtest/gtest.h"
#include "MediumPage.hpp"
#include "TypeInfo.h"

namespace {

using MediumPage = typename kotlin::alloc::MediumPage;
using Cell = typename kotlin::alloc::Cell;

TypeInfo fakeType = {.flags_ = 0}; // a type without a finalizer

#define MIN_BLOCK_SIZE (SMALL_PAGE_MAX_BLOCK_SIZE + 1)

void mark(void* obj) {
    reinterpret_cast<uint64_t*>(obj)[0] = 1;
}

uint8_t* alloc(MediumPage* page, uint32_t blockSize) {
    uint8_t* ptr = page->TryAllocate(blockSize);
    if (!page->CheckInvariants()) {
        ADD_FAILURE();
        return nullptr;
    }
    if (ptr == nullptr) return nullptr;
    memset(ptr, 0, 8 * blockSize);
    reinterpret_cast<uint64_t*>(ptr)[1] = reinterpret_cast<uint64_t>(&fakeType);
    if (!page->CheckInvariants()) {
        ADD_FAILURE();
        return nullptr;
    }
    return ptr;
}

TEST(CustomAllocTest, MediumPageAlloc) {
    MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
    uint8_t* p1 = alloc(page, MIN_BLOCK_SIZE);
    uint8_t* p2 = alloc(page, MIN_BLOCK_SIZE);
    uint64_t dist = abs(p1 - p2);
    EXPECT_EQ(dist, (MIN_BLOCK_SIZE + 1) * sizeof(kotlin::alloc::Cell));
    page->Destroy();
}

TEST(CustomAllocTest, MediumPageSweepEmptyPage) {
    MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
    EXPECT_FALSE(page->Sweep());
    page->Destroy();
}

TEST(CustomAllocTest, MediumPageSweepFullUnmarkedPage) {
    for (uint32_t seed = 0xC0FFEE0; seed <= 0xC0FFEEF; ++seed) {
        std::minstd_rand r(seed);
        MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
        while (alloc(page, MIN_BLOCK_SIZE + r() % 100)) {}
        EXPECT_FALSE(page->Sweep());
        page->Destroy();
    }
}

TEST(CustomAllocTest, MediumPageSweepSingleMarked) {
    MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
    mark(alloc(page, MIN_BLOCK_SIZE));
    EXPECT_TRUE(page->Sweep());
    page->Destroy();
}

TEST(CustomAllocTest, MediumPageSweepSingleReuse) {
    for (uint32_t seed = 0xC0FFEE0; seed <= 0xC0FFEEF; ++seed) {
        std::minstd_rand r(seed);
        MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
        int count1 = 0;
        while (alloc(page, MIN_BLOCK_SIZE + r() % 100)) ++count1;
        EXPECT_FALSE(page->Sweep());
        r.seed(seed);
        int count2 = 0;
        while (alloc(page, MIN_BLOCK_SIZE + r() % 100)) ++count2;
        EXPECT_EQ(count1, count2);
        page->Destroy();
    }
}

TEST(CustomAllocTest, MediumPageSweepReuse) {
    for (uint32_t seed = 0xC0FFEE0; seed <= 0xC0FFEEF; ++seed) {
        std::minstd_rand r(seed);
        MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
        int unmarked = 0;
        while (true) {
            uint8_t* ptr = alloc(page, MIN_BLOCK_SIZE);
            if (ptr == nullptr) break;
            if (r() & 1) {
                mark(ptr);
            } else {
                ++unmarked;
            }
        }
        page->Sweep();
        int freed = 0;
        while (alloc(page, MIN_BLOCK_SIZE)) ++freed;
        EXPECT_EQ(freed, unmarked);
        page->Destroy();
    }
}

TEST(CustomAllocTest, MediumPageSweepCoallesce) {
    MediumPage* page = MediumPage::Create(MIN_BLOCK_SIZE);
    EXPECT_TRUE(alloc(page, (MEDIUM_PAGE_CELL_COUNT-1) / 2 - 1));
    EXPECT_FALSE(page->Sweep());
    EXPECT_TRUE(alloc(page, (MEDIUM_PAGE_CELL_COUNT-1) - 1));
    page->Destroy();
}

#undef MIN_BLOCK_SIZE
} // namespace
