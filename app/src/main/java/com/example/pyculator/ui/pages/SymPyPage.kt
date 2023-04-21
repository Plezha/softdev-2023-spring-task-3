package com.example.pyculator.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.pyculator.R
import com.example.pyculator.utils.symPy
import com.example.pyculator.viewmodels.FavoriteVariable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val funcs = listOf("Abs", "AccumBounds", "Add", "Adjoint", "AlgebraicField", "AlgebraicNumber", "And", "AppliedPredicate", "Array", "AssumptionsContext", "Atom", "AtomicExpr", "BasePolynomialError", "Basic", "BlockDiagMatrix", "BlockMatrix", "CC", "CRootOf", "Catalan", "Chi", "Ci", "Circle", "CoercionFailed", "Complement", "ComplexField", "ComplexRegion", "ComplexRootOf", "Complexes", "ComputationFailed", "ConditionSet", "Contains", "CosineTransform", "Curve", "DeferredVector", "DenseNDimArray", "Derivative", "Determinant", "DiagMatrix", "DiagonalMatrix", "DiagonalOf", "Dict", "DiracDelta", "DisjointUnion", "Domain", "DomainError", "DotProduct", "Dummy", "E", "E1", "EPath", "EX", "EXRAW", "Ei", "Eijk", "Ellipse", "EmptySequence", "EmptySet", "Eq", "Equality", "Equivalent", "EulerGamma", "EvaluationFailed", "ExactQuotientFailed", "Expr", "ExpressionDomain", "ExtraneousFactors", "FF", "FF_gmpy", "FF_python", "FU", "FallingFactorial", "FiniteField", "FiniteSet", "FlagError", "Float", "FourierTransform", "FractionField", "Function", "FunctionClass", "FunctionMatrix", "GF", "GMPYFiniteField", "GMPYIntegerRing", "GMPYRationalField", "Ge", "GeneratorsError", "GeneratorsNeeded", "GeometryError", "GoldenRatio", "GramSchmidt", "GreaterThan", "GroebnerBasis", "Gt", "HadamardPower", "HadamardProduct", "HankelTransform", "Heaviside", "HeuristicGCDFailed", "HomomorphismFailed", "I", "ITE", "Id", "Identity", "Idx", "ImageSet", "ImmutableDenseMatrix", "ImmutableDenseNDimArray", "ImmutableMatrix", "ImmutableSparseMatrix", "ImmutableSparseNDimArray", "Implies", "Indexed", "IndexedBase", "Integer", "IntegerRing", "Integers", "Integral", "Intersection", "Interval", "Inverse", "InverseCosineTransform", "InverseFourierTransform", "InverseHankelTransform", "InverseLaplaceTransform", "InverseMellinTransform", "InverseSineTransform", "IsomorphismFailed", "KroneckerDelta", "KroneckerProduct", "LC", "LM", "LT", "Lambda", "LambertW", "LaplaceTransform", "Le", "LessThan", "LeviCivita", "Li", "Limit", "Line", "Line2D", "Line3D", "Lt", "MatAdd", "MatMul", "MatPow", "Matrix", "MatrixBase", "MatrixExpr", "MatrixPermute", "MatrixSlice", "MatrixSymbol", "Max", "MellinTransform", "Min", "Mod", "Monomial", "Mul", "MultivariatePolynomialError", "MutableDenseMatrix", "MutableDenseNDimArray", "MutableMatrix", "MutableSparseMatrix", "MutableSparseNDimArray", "N", "NDimArray", "Nand", "Naturals", "Naturals0", "Ne", "NonSquareMatrixError", "Nor", "Not", "NotAlgebraic", "NotInvertible", "NotReversible", "Number", "NumberSymbol", "O", "OmegaPower", "OneMatrix", "OperationNotSupported", "OptionError", "Options", "Or", "Order", "Ordinal", "POSform", "Parabola", "Permanent", "PermutationMatrix", "Piecewise", "Plane", "Point", "Point2D", "Point3D", "PoleError", "PolificationFailed", "Poly", "Polygon", "PolynomialDivisionFailed", "PolynomialError", "PolynomialRing", "Pow", "PowerSet", "PrecisionExhausted", "Predicate", "Product", "ProductSet", "PurePoly", "PythonFiniteField", "PythonIntegerRing", "PythonRational", "Q", "QQ", "QQ_I", "QQ_gmpy", "QQ_python", "Quaternion", "RR", "Range", "Rational", "RationalField", "Rationals", "Ray", "Ray2D", "Ray3D", "RealField", "RealNumber", "Reals", "RefinementFailed", "RegularPolygon", "Rel", "Rem", "RisingFactorial", "RootOf", "RootSum", "S", "SOPform", "Segment", "Segment2D", "Segment3D", "SeqAdd", "SeqFormula", "SeqMul", "SeqPer", "Set", "ShapeError", "Shi", "Si", "Sieve", "SineTransform", "SingularityFunction", "SparseMatrix", "SparseNDimArray", "StrPrinter", "StrictGreaterThan", "StrictLessThan", "Subs", "Sum", "Symbol", "SymmetricDifference", "SympifyError", "TableForm", "Trace", "Transpose", "Triangle", "TribonacciConstant", "Tuple", "Unequality", "UnevaluatedExpr", "UnificationFailed", "Union", "UnivariatePolynomialError", "UniversalSet", "Wild", "WildFunction", "Xor", "Ynm", "Ynm_c", "ZZ", "ZZ_I", "ZZ_gmpy", "ZZ_python", "ZeroMatrix", "Znm", "__version__", "abundance", "acos", "acosh", "acot", "acoth", "acsc", "acsch", "adjoint", "airyai", "airyaiprime", "airybi", "airybiprime", "algebras", "apart", "apart_list", "appellf1", "apply_finite_diff", "approximants", "are_similar", "arg", "arity", "asec", "asech", "asin", "asinh", "ask", "assemble_partfrac_list", "assoc_laguerre", "assoc_legendre", "assuming", "assumptions", "atan", "atan2", "atanh", "banded", "bell", "bernoulli", "besseli", "besselj", "besselk", "besselsimp", "bessely", "beta", "betainc", "betainc_regularized", "binomial", "binomial_coefficients", "binomial_coefficients_list", "block_collapse", "blockcut", "bool_map", "bottom_up", "bspline_basis", "bspline_basis_set", "cacheit", "calculus", "cancel", "capture", "carmichael", "cartes", "casoratian", "catalan", "cbrt", "ccode", "ceiling", "centroid", "chebyshevt", "chebyshevt_poly", "chebyshevt_root", "chebyshevu", "chebyshevu_poly", "chebyshevu_root", "check_assumptions", "checkodesol", "checkpdesol", "checksol", "classify_ode", "classify_pde", "closest_points", "cofactors", "collect", "collect_const", "combsimp", "comp", "compose", "composite", "compositepi", "concrete", "conjugate", "construct_domain", "content", "continued_fraction", "continued_fraction_convergents", "continued_fraction_iterator", "continued_fraction_periodic", "continued_fraction_reduce", "convex_hull", "convolution", "cos", "cosh", "cosine_transform", "cot", "coth", "count_ops", "count_roots", "covering_product", "csc", "csch", "cse", "cxxcode", "cycle_length", "cyclotomic_poly", "decompogen", "decompose", "default_sort_key", "deg", "degree", "degree_list", "denom", "derive_by_array", "det", "det_quick", "diag", "diagonalize_vector", "dict_merge", "diff", "difference_delta", "differentiate_finite", "digamma", "diophantine", "dirichlet_eta", "discrete", "discrete_log", "discriminant", "div", "divisor_count", "divisor_sigma", "divisors", "doctest", "dotprint", "dsolve", "egyptian_fraction", "elliptic_e", "elliptic_f", "elliptic_k", "elliptic_pi", "epath", "erf", "erf2", "erf2inv", "erfc", "erfcinv", "erfi", "erfinv", "euler", "euler_equations", "evalf", "evaluate", "exp", "exp_polar", "expand", "expand_complex", "expand_func", "expand_log", "expand_mul", "expand_multinomial", "expand_power_base", "expand_power_exp", "expand_trig", "expint", "exptrigsimp", "exquo", "external", "eye", "factor", "factor_list", "factor_nc", "factor_terms", "factorial", "factorial2", "factorint", "factorrat", "failing_assumptions", "false", "farthest_points", "fcode", "ff", "fft", "fibonacci", "field", "field_isomorphism", "filldedent", "finite_diff_weights", "flatten", "floor", "fourier_series", "fourier_transform", "fps", "frac", "fraction", "fresnelc", "fresnels", "fu", "functions", "fwht", "gamma", "gammasimp", "gcd", "gcd_list", "gcd_terms", "gcdex", "gegenbauer", "genocchi", "geometry", "get_contraction_structure", "get_indices", "gff", "gff_list", "glsl_code", "grevlex", "grlex", "groebner", "ground_roots", "group", "gruntz", "hadamard_product", "half_gcdex", "hankel1", "hankel2", "hankel_transform", "harmonic", "has_dups", "has_variety", "hermite", "hermite_poly", "hessian", "hn1", "hn2", "homogeneous_order", "horner", "hyper", "hyperexpand", "hypersimilar", "hypersimp", "idiff", "ifft", "ifwht", "igcd", "igrevlex", "igrlex", "ilcm", "ilex", "im", "imageset", "init_printing", "init_session", "integer_log", "integer_nthroot", "integrate", "interactive", "interactive_traversal", "interpolate", "interpolating_poly", "interpolating_spline", "intersecting_product", "intersection", "intervals", "intt", "inv_quick", "inverse_cosine_transform", "inverse_fourier_transform", "inverse_hankel_transform", "inverse_laplace_transform", "inverse_mellin_transform", "inverse_mobius_transform", "inverse_sine_transform", "invert", "is_abundant", "is_amicable", "is_convex", "is_decreasing", "is_deficient", "is_increasing", "is_mersenne_prime", "is_monotonic", "is_nthpow_residue", "is_perfect", "is_primitive_root", "is_quad_residue", "is_strictly_decreasing", "is_strictly_increasing", "is_zero_dimensional", "isolate", "isprime", "itermonomials", "jacobi", "jacobi_normalized", "jacobi_poly", "jacobi_symbol", "jn", "jn_zeros", "jordan_cell", "jscode", "julia_code", "kronecker_product", "kroneckersimp", "laguerre", "laguerre_poly", "lambdify", "laplace_transform", "latex", "lcm", "lcm_list", "legendre", "legendre_poly", "legendre_symbol", "lerchphi", "lex", "li", "limit", "limit_seq", "line_integrate", "linear_eq_to_matrix", "linsolve", "list2numpy", "ln", "log", "logcombine", "loggamma", "lowergamma", "lucas", "maple_code", "marcumq", "mathematica_code", "mathieuc", "mathieucprime", "mathieus", "mathieusprime", "mathml", "matrix2numpy", "matrix_multiply_elementwise", "matrix_symbols", "maximum", "meijerg", "mellin_transform", "memoize_property", "mersenne_prime_exponent", "minimal_polynomial", "minimum", "minpoly", "mobius", "mobius_transform", "mod_inverse", "monic", "motzkin", "multigamma", "multiline_latex", "multinomial_coefficients", "multipledispatch", "multiplicity", "n_order", "nan", "nextprime", "nfloat", "nonlinsolve", "not_empty_in", "npartitions", "nroots", "nsimplify", "nsolve", "nth_power_roots_poly", "ntheory", "nthroot_mod", "ntt", "numbered_symbols", "numer", "octave_code", "ode_order", "ones", "oo", "ord0", "ordered", "pager_print", "parallel_poly_from_expr", "parse_expr", "parsing", "partition", "pde_separate", "pde_separate_add", "pde_separate_mul", "pdiv", "pdsolve", "per", "perfect_power", "periodic_argument", "periodicity", "permutedims", "pexquo", "pi", "piecewise_exclusive", "piecewise_fold", "plot", "plot_backends", "plot_implicit", "plot_parametric", "plotting", "polar_lift", "polarify", "pollard_pm1", "pollard_rho", "poly", "poly_from_expr", "polygamma", "polylog", "polys", "posify", "postfixes", "postorder_traversal", "powdenest", "powsimp", "pprint", "pprint_try_use_unicode", "pprint_use_unicode", "pquo", "prefixes", "prem", "preorder_traversal", "pretty", "pretty_print", "preview", "prevprime", "prime", "prime_decomp", "prime_valuation", "primefactors", "primenu", "primeomega", "primepi", "primerange", "primitive", "primitive_element", "primitive_root", "primorial", "principal_branch", "print_ccode", "print_fcode", "print_glsl", "print_gtk", "print_jscode", "print_latex", "print_maple_code", "print_mathml", "print_python", "print_rcode", "print_tree", "printing", "prod", "product", "proper_divisor_count", "proper_divisors", "public", "pycode", "python", "quadratic_congruence", "quadratic_residues", "quo", "rad", "radsimp", "randMatrix", "random_poly", "randprime", "rational_interpolate", "ratsimp", "ratsimpmodprime", "rcode", "rcollect", "re", "real_root", "real_roots", "reduce_abs_inequalities", "reduce_abs_inequality", "reduce_inequalities", "reduced", "reduced_totient", "refine", "refine_root", "register_handler", "release", "rem", "remove_handler", "reshape", "residue", "resultant", "rf", "riemann_xi", "ring", "root", "rootof", "roots", "rot_axis1", "rot_axis2", "rot_axis3", "rotations", "round_two", "rsolve", "rsolve_hyper", "rsolve_poly", "rsolve_ratio", "rust_code", "s", "satisfiable", "sec", "sech", "separatevars", "sequence", "series", "seterr", "sfield", "shape", "sieve", "sift", "sign", "signsimp", "simplify", "simplify_logic", "sin", "sinc", "sine_transform", "singularities", "singularityintegrate", "sinh", "solve", "solve_linear", "solve_linear_system", "solve_linear_system_LU", "solve_poly_inequality", "solve_poly_system", "solve_rational_inequalities", "solve_triangulated", "solve_undetermined_coeffs", "solve_univariate_inequality", "solveset", "source", "sqf", "sqf_list", "sqf_norm", "sqf_part", "sqrt", "sqrt_mod", "sqrt_mod_iter", "sqrtdenest", "srepr", "sring", "sstr", "sstrrepr", "stationary_points", "stieltjes", "strategies", "sturm", "subfactorial", "subresultants", "subsets", "substitution", "summation", "swinnerton_dyer_poly", "symarray", "symbols", "symmetric_poly", "symmetrize", "sympify", "take", "tan", "tanh", "tensor", "tensorcontraction", "tensordiagonal", "tensorproduct", "terms_gcd", "test", "textplot", "threaded", "timed", "to_cnf", "to_dnf", "to_nnf", "to_number_field", "together", "topological_sort", "total_degree", "totient", "trace", "trailing", "transpose", "tribonacci", "trigamma", "trigsimp", "true", "trunc", "unbranched_argument", "unflatten", "unpolarify", "uppergamma", "use", "utilities", "var", "variations", "vectorize", "vfield", "viete", "vring", "wronskian", "xfield", "xring", "xthreaded", "yn", "zeros", "zeta", "zoo")
val funcsForFunction = listOf(
    "integrate" to "Integral",
    "roots" to "Roots",
    "diff" to "Derivative",
    "is_decreasing" to "Decreasing",
    "is_increasing" to "Increasing",
)
val funcsForNumber = listOf(
    "primefactors" to "Prime factors",
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SymPyPage(
    coroutineScope: CoroutineScope,
    memoryList: SnapshotStateList<FavoriteVariable>,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    var toSymPy by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.padding(5.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy((-5).dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = toSymPy,
                onValueChange = { toSymPy = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard?.hide()
                    }
                ),
            )

            Text(
                result,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            )
        }



        /*
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = {
                memoryList.add(FavoriteVariable("a${memoryList.size+1}", result, toSymPy))
            },
        ) {
            Icon(painterResource(id = R.drawable.baseline_save_alt_24), contentDescription = null)
        }
        */

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = {
                result = ""
                coroutineScope.launch {
                    if ( toSymPy.matches(Regex("""-?\d+""")) ) {
                        toSymPy.toBigInteger()
                        for ((func, funcName) in funcsForNumber) {
                            val res = symPy(func, toSymPy)

                            result += "$funcName: $res\n"
                            scrollState.scrollTo(scrollState.maxValue)
                        }
                    } else {
                        for ((func, funcName) in funcsForFunction) {
                            val res = symPy(func, toSymPy)
                            result += "$funcName: $res\n"
                            scrollState.scrollTo(scrollState.maxValue)
                        }
                    }
                }
            }
        ) {
            Icon(painterResource(R.drawable.baseline_play_arrow_24), contentDescription = null)
        }

    }
}