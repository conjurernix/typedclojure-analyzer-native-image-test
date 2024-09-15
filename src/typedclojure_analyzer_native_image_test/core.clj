(ns typedclojure-analyzer-native-image-test.core
  (:require [typed.clj.analyzer :as t.ana]
            [typed.cljc.analyzer :as ana])
  (:gen-class)
  (:import (clojure.lang RT)))


#_(defn -main [& _]
    (print (t.ana/analyze '(for [i (range 10)] i))))

(defn -main [& _]
  (with-bindings {Compiler/LOADER        (RT/makeClassLoader)
                  #'ana/macroexpand-1    t.ana/macroexpand-1
                  #'ana/create-var       t.ana/create-var
                  #'ana/scheduled-passes @t.ana/scheduled-default-passes
                  #'ana/parse            t.ana/parse
                  #'ana/var?             var?
                  #'ana/resolve-ns       t.ana/resolve-ns
                  #'ana/resolve-sym      t.ana/resolve-sym
                  #'ana/unanalyzed       t.ana/unanalyzed
                  #'ana/analyze-outer    t.ana/analyze-outer
                  #'ana/current-ns-name  t.ana/current-ns-name}
    (print (ana/macroexpand-1 '(for [i (range 10)] i)))))

