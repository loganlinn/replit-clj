(ns build
  (:refer-clojure :exclude [compile])
  (:require
   [clojure.tools.build.api :as b]))

(def lib (symbol (System/getenv "REPL_OWNER") (System/getenv "REPL_SLUG")))
(def main 'main)
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))

(def version (format "1.2.%s" (b/git-count-revs nil)))
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def uber-file (format "target/%s-%s-standalone.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn pom [_]
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]}))

(defn jar [m]
  (pom m)
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))

(defn uber [m]
  (clean m)
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis basis
                  :src-dirs ["src"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis basis
           :main main}))

(defn compile [_]
  (b/javac
   {:src-dirs ["java"]
    :class-dir class-dir
    :basis basis}))