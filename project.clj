(defproject com.github.strojure/vectops "1.0.30"
  :description "Basic operations with Clojure vectors."
  :url "https://github.com/strojure/vectops"
  :license {:name "The Unlicense" :url "https://unlicense.org"}

  :dependencies []

  :profiles {:provided {:dependencies [[org.clojure/clojure "1.11.1"]
                                       [org.clojure/clojurescript "1.11.60"]
                                       ;; clojurescript tests
                                       [com.google.guava/guava "31.1-jre"]
                                       [olical/cljs-test-runner "3.8.0"]]}
             :dev {:source-paths ["doc"]}}

  :aliases {"cljs-test" ["run" "-m" "cljs-test-runner.main"]}

  :clean-targets ["target" "cljs-test-runner-out"]

  :deploy-repositories [["clojars" {:url "https://clojars.org/repo" :sign-releases false}]])
