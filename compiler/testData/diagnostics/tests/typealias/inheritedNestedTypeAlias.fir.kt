// FIR_DISABLE_LAZY_RESOLVE_CHECKS
// !DIAGNOSTICS: -UNUSED_VARIABLE -UNUSED_PARAMETER -TOPLEVEL_TYPEALIASES_ONLY

interface ICell<T> {
    val x: T
}

class Cell<T>(override val x: T): ICell<T>

open class Base<T> {
    typealias CT = Cell<T>
    inner class InnerCell(override val x: T): ICell<T>
}

class Derived : Base<Int>() {
    val x1: InnerCell = InnerCell(42)
    val x2: Base<Int>.InnerCell = InnerCell(42)

    val test1: <!UNRESOLVED_REFERENCE!>CT<!> = Cell(42)
    val test2: Base<Int>.CT = <!TYPE_MISMATCH, TYPE_MISMATCH!>Cell(42)<!>
}
