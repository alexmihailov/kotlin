// FIR_DISABLE_LAZY_RESOLVE_CHECKS
// !LANGUAGE: +SuspendFunctionAsSupertype
// SKIP_TXT
// DIAGNOSTICS: -CONFLICTING_INHERITED_MEMBERS, -CONFLICTING_OVERLOADS

class C: <!MIXING_SUSPEND_AND_NON_SUSPEND_SUPERTYPES!>suspend () -> Unit, () -> Unit<!> {
    override suspend fun invoke() {
    }
}

fun interface FI: <!MIXING_SUSPEND_AND_NON_SUSPEND_SUPERTYPES!>suspend () -> Unit, () -> Unit<!> {
}

interface I: <!MIXING_SUSPEND_AND_NON_SUSPEND_SUPERTYPES!>suspend () -> Unit, () -> Unit<!> {
}

object O: <!MIXING_SUSPEND_AND_NON_SUSPEND_SUPERTYPES!>suspend () -> Unit, () -> Unit<!> {
    override suspend fun invoke() {
    }
}
