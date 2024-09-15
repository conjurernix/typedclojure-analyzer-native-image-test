# Test project compiling typed.clj.analyzer with GraalVM native image

This project is a test to compile the `typed.clj.analyzer` project with GraalVM native image,
to see if it is possible to compile a Clojure project that uses `typedclojure` with GraalVM native image.
Uses https://github.com/taylorwood/clj.native-image to compile the project.

Project contains two main functions as examples, one that calls the `typed.clj.analyzer/analyze`, and one that calls the `typed.clj.analyzer/macroexpand-1`.
form, and prints the result. This is to test if the `typed.clj.analyzer` project can be compiled with GraalVM native image.
In order to typecheck macros, `typedclojure` expands the macro and typechecks the result. This might happen at runtime,
thus, there might be issues with compiling `typedclojure` projects with GraalVM native image, as it might require
evaluating code at runtime.

## Usage
First make sure you have GraalVM installed, and the `native-image` tool is available in your PATH.
Easiest way is to use sdkman to install GraalVM:
```bash
# Install sdkman
sdk install java 21.0.2-graalce
```
As tested on the above version, `native-image` is added automatically to your path.


To compile the project with GraalVM native image, run the following command:
```bash
# Compile the project with GraalVM native image
clj -M:native-image --verbose
```

The project successfully compiles, but generates a fallback image by default for both, unless `--no-fallback` flag is used.
```bash
Warning: Image 'core' is a fallback image that requires a JDK for execution (use --no-fallback to suppress fallback image generation and to print more detailed information why a fallback image was necessary).
```
Running it with `--no-fallback` flag compiles for both, but the `analyze` example fails when ran,
whereas the `macroexpand-1` example works as expected.

```bash
Exception in thread "main" clojure.lang.ExceptionInfo: Class not found: clojure.lang.LazySeq {:class clojure.lang.LazySeq, :file "NO_SOURCE_PATH", :column 25, :line 7}
	at typed.clj.analyzer.passes.validate$fn__4002.invokeStatic(validate.clj:32)
	at typed.clj.analyzer.passes.validate$fn__4002.invoke(validate.clj:28)
	at clojure.lang.MultiFn.invoke(MultiFn.java:229)
	at typed.clj.analyzer.passes.validate$validate.invokeStatic(validate.clj:280)
	at typed.clj.analyzer.passes.validate$validate.invoke(validate.clj:250)
	at typed.cljc.analyzer.passes$compile_passes$pfns_fn__3221$fn__3222$fn__3226.invoke(passes.clj:57)
	at typed.cljc.analyzer.passes$compile_passes$pfns_fn__3221$fn__3222$fn__3226.invoke(passes.clj:57)
	at typed.cljc.analyzer$run_post_passes.invokeStatic(analyzer.cljc:89)
	at typed.cljc.analyzer$run_post_passes.invoke(analyzer.cljc:87)
	at clojure.core$comp$fn__5876.invoke(core.clj:2586)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:114)
	at typed.cljc.analyzer.ast$walk$walk__1520$walk__1521.invoke(ast.cljc:108)
	at typed.cljc.analyzer$update_newexpr_children.invokeStatic(analyzer.cljc:616)
	at typed.cljc.analyzer$update_newexpr_children.invoke(analyzer.cljc:613)
	at typed.cljc.analyzer.NewExpr.update_children_STAR_(analyzer.cljc:611)
	at typed.cljc.analyzer.ast$update_children_reduced.invokeStatic(ast.cljc:73)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:111)
	at typed.cljc.analyzer.ast$walk$walk__1520$walk__1521.invoke(ast.cljc:108)
	at typed.cljc.analyzer$update_doexpr_children.invokeStatic(analyzer.cljc:548)
	at typed.cljc.analyzer$update_doexpr_children.invoke(analyzer.cljc:545)
	at typed.cljc.analyzer.DoExpr.update_children_STAR_(analyzer.cljc:543)
	at typed.cljc.analyzer.ast$update_children_reduced.invokeStatic(ast.cljc:73)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:111)
	at typed.cljc.analyzer.ast$walk$walk__1520$walk__1521.invoke(ast.cljc:108)
	at typed.cljc.analyzer$update_fnmethodexpr_children.invokeStatic(analyzer.cljc:1046)
	at typed.cljc.analyzer$update_fnmethodexpr_children.invoke(analyzer.cljc:1043)
	at typed.cljc.analyzer.FnMethodExpr.update_children_STAR_(analyzer.cljc:1041)
	at typed.cljc.analyzer.ast$update_children_reduced.invokeStatic(ast.cljc:73)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:111)
	at typed.cljc.analyzer.ast$walk$walk__1520$walk__1521.invoke(ast.cljc:108)
	at clojure.core$mapv$fn__8535.invoke(core.clj:6980)
	at clojure.lang.PersistentVector.reduce(PersistentVector.java:343)
	at clojure.core$reduce.invokeStatic(core.clj:6886)
	at clojure.core$mapv.invokeStatic(core.clj:6971)
	at typed.cljc.analyzer$f__GT_fs$fn__1575.invoke(analyzer.cljc:223)
	at typed.cljc.analyzer$update_fnexpr_children.invokeStatic(analyzer.cljc:1131)
	at typed.cljc.analyzer$update_fnexpr_children.invoke(analyzer.cljc:1128)
	at typed.cljc.analyzer.FnExpr.update_children_STAR_(analyzer.cljc:1126)
	at typed.cljc.analyzer.ast$update_children_reduced.invokeStatic(ast.cljc:73)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:111)
	at typed.cljc.analyzer.ast$walk$walk__1520$walk__1521.invoke(ast.cljc:108)
	at typed.cljc.analyzer.BindingExpr.update_children_STAR_(analyzer.cljc:793)
	at typed.cljc.analyzer.ast$update_children_reduced.invokeStatic(ast.cljc:73)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:111)
	at typed.cljc.analyzer.ast$walk$walk__1520$walk__1521.invoke(ast.cljc:108)
	at clojure.core$mapv$fn__8535.invoke(core.clj:6980)
	at clojure.lang.PersistentVector.reduce(PersistentVector.java:343)
	at clojure.core$reduce.invokeStatic(core.clj:6886)
	at clojure.core$mapv.invokeStatic(core.clj:6971)
	at typed.cljc.analyzer$f__GT_fs$fn__1575.invoke(analyzer.cljc:223)
	at typed.cljc.analyzer$update_letexpr_children.invokeStatic(analyzer.cljc:957)
	at typed.cljc.analyzer$update_letexpr_children.invoke(analyzer.cljc:954)
	at typed.cljc.analyzer.LetExpr.update_children_STAR_(analyzer.cljc:952)
	at typed.cljc.analyzer.ast$update_children_reduced.invokeStatic(ast.cljc:73)
	at typed.cljc.analyzer.ast$walk$walk__1520.invoke(ast.cljc:111)
	at typed.cljc.analyzer.ast$walk.invokeStatic(ast.cljc:107)
	at typed.cljc.analyzer.ast$walk.invoke(ast.cljc:96)
	at typed.cljc.analyzer.ast$walk.invokeStatic(ast.cljc:104)
	at typed.cljc.analyzer$run_passes.invokeStatic(analyzer.cljc:93)
	at typed.clj.analyzer$analyze$fn__4615$f__3204__auto____4616.invoke(analyzer.clj:586)
	at typed.clj.analyzer$analyze$fn__4615.invoke(analyzer.clj:584)
	at clojure.lang.AFn.applyToHelper(AFn.java:152)
	at clojure.lang.AFn.applyTo(AFn.java:144)
	at clojure.core$apply.invokeStatic(core.clj:667)
	at clojure.core$with_bindings_STAR_.invokeStatic(core.clj:1990)
	at clojure.core$with_bindings_STAR_.doInvoke(core.clj:1990)
	at clojure.lang.RestFn.invoke(RestFn.java:425)
	at typed.clj.analyzer$analyze.invokeStatic(analyzer.clj:570)
	at typed.clj.analyzer$analyze.invoke(analyzer.clj:550)
	at typed.clj.analyzer$analyze.invokeStatic(analyzer.clj:567)
	at typedclojure_analyzer_native_image_test.core$_main.invokeStatic(core.clj:6)
	at typedclojure_analyzer_native_image_test.core$_main.invoke(core.clj:6)
	at clojure.lang.AFn.applyToHelper(AFn.java:152)
	at clojure.lang.AFn.applyTo(AFn.java:144)
	at typedclojure_analyzer_native_image_test.core.main(Unknown Source)
	at java.base@21.0.2/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
```
