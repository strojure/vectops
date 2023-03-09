(defproject com.github.strojure/vectops "1.1.23-SNAPSHOT"
  :description "Basic operations with Clojure vectors."
  :url "https://github.com/strojure/vectops"
  :license {:name "The Unlicense" :url "https://unlicense.org"}

  :dependencies []

  :profiles {:provided {:dependencies [[org.clojure/clojure "1.11.1"]
                                       [org.clojure/clojurescript "1.11.60"]
                                       ;; clojurescript repl deps
                                       [com.google.guava/guava "31.1-jre"]]}}

  :deploy-repositories [["clojars" {:url "https://clojars.org/repo" :sign-releases false}]])
