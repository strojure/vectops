(ns benchmarks.insert-at
  (:require [strojure.vectops.core :as vec]))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(def ^:private v10 (into [] (range 10)))

(def ^:private v1000 (into [] (range 1000)))

(defn- simple-insert-at
  [vct idx obj]
  (into (conj (subvec vct 0 idx) obj)
        (subvec vct idx)))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
;;; Short vector

;; insert first

(vec/insert-at v10 0 :x)
;Evaluation count : 2038422 in 6 samples of 339737 calls.
;             Execution time mean : 338,976221 ns
;    Execution time std-deviation : 79,953947 ns
;   Execution time lower quantile : 283,215196 ns ( 2,5%)
;   Execution time upper quantile : 442,707943 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 7.505000 msecs

(simple-insert-at v10 0 :x)
;Evaluation count : 842568 in 6 samples of 140428 calls.
;             Execution time mean : 669,461848 ns
;    Execution time std-deviation : 9,842805 ns
;   Execution time lower quantile : 657,251082 ns ( 2,5%)
;   Execution time upper quantile : 679,832583 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 15.663600 msecs

;; insert in the middle

(vec/insert-at v10 5 :x)
;Evaluation count : 2366778 in 6 samples of 394463 calls.
;             Execution time mean : 296,996762 ns
;    Execution time std-deviation : 71,688283 ns
;   Execution time lower quantile : 243,687537 ns ( 2,5%)
;   Execution time upper quantile : 389,152860 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 5.205200 msecs

(simple-insert-at v10 5 :x)
;Evaluation count : 1356450 in 6 samples of 226075 calls.
;             Execution time mean : 537,214600 ns
;    Execution time std-deviation : 164,881686 ns
;   Execution time lower quantile : 442,438235 ns ( 2,5%)
;   Execution time upper quantile : 818,251184 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 9.722400 msecs

;; insert before last

(vec/insert-at v10 9 :x)
;Evaluation count : 2666598 in 6 samples of 444433 calls.
;             Execution time mean : 260,580442 ns
;    Execution time std-deviation : 57,722604 ns
;   Execution time lower quantile : 220,236641 ns ( 2,5%)
;   Execution time upper quantile : 337,325268 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.323800 msecs

(simple-insert-at v10 9 :x)
;Evaluation count : 2314758 in 6 samples of 385793 calls.
;             Execution time mean : 278,932202 ns
;    Execution time std-deviation : 5,556546 ns
;   Execution time lower quantile : 271,575148 ns ( 2,5%)
;   Execution time upper quantile : 283,910681 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 4.983200 msecs

;; insert last

(vec/insert-at v10 10 :x)
;Evaluation count : 20726682 in 6 samples of 3454447 calls.
;             Execution time mean : 31,544095 ns
;    Execution time std-deviation : 9,159785 ns
;   Execution time lower quantile : 21,615709 ns ( 2,5%)
;   Execution time upper quantile : 40,834484 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 0.898100 msecs

(simple-insert-at v10 10 :x)
;Evaluation count : 2799816 in 6 samples of 466636 calls.
;             Execution time mean : 213,250899 ns
;    Execution time std-deviation : 2,158299 ns
;   Execution time lower quantile : 210,782344 ns ( 2,5%)
;   Execution time upper quantile : 215,978713 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.120800 msecs

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
;;; Long vector

;; insert first

(vec/insert-at v1000 0 :x)
;Evaluation count : 28128 in 6 samples of 4688 calls.
;             Execution time mean : 20,781132 µs
;    Execution time std-deviation : 727,936370 ns
;   Execution time lower quantile : 20,105413 µs ( 2,5%)
;   Execution time upper quantile : 21,857869 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 701.267500 msecs

(simple-insert-at v1000 0 :x)
;Evaluation count : 23154 in 6 samples of 3859 calls.
;             Execution time mean : 26,157063 µs
;    Execution time std-deviation : 313,141148 ns
;   Execution time lower quantile : 25,814474 µs ( 2,5%)
;   Execution time upper quantile : 26,547371 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 1949.199200 msecs

;; insert in the middle

(vec/insert-at v1000 500 :x)
;Evaluation count : 59442 in 6 samples of 9907 calls.
;             Execution time mean : 10,219044 µs
;    Execution time std-deviation : 145,956332 ns
;   Execution time lower quantile : 10,083619 µs ( 2,5%)
;   Execution time upper quantile : 10,458947 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 337.468800 msecs

(simple-insert-at v1000 500 :x)
;Evaluation count : 16122 in 6 samples of 2687 calls.
;             Execution time mean : 51,771809 µs
;    Execution time std-deviation : 13,303230 µs
;   Execution time lower quantile : 38,486429 µs ( 2,5%)
;   Execution time upper quantile : 71,867244 µs (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 985.487100 msecs

;; insert before last

(vec/insert-at v1000 999 :x)
;Evaluation count : 2632404 in 6 samples of 438734 calls.
;             Execution time mean : 246,026145 ns
;    Execution time std-deviation : 16,657301 ns
;   Execution time lower quantile : 225,959506 ns ( 2,5%)
;   Execution time upper quantile : 263,175621 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.178400 msecs

(simple-insert-at v1000 999 :x)
;Evaluation count : 2183508 in 6 samples of 363918 calls.
;             Execution time mean : 296,537984 ns
;    Execution time std-deviation : 57,687571 ns
;   Execution time lower quantile : 262,716079 ns ( 2,5%)
;   Execution time upper quantile : 393,635201 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 4.392800 msecs

;; insert last

(vec/insert-at v1000 1000 :x)
;Evaluation count : 19331544 in 6 samples of 3221924 calls.
;             Execution time mean : 26,586617 ns
;    Execution time std-deviation : 4,233021 ns
;   Execution time lower quantile : 22,517697 ns ( 2,5%)
;   Execution time upper quantile : 31,260090 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 0.796100 msecs

(simple-insert-at v1000 1000 :x)
;Evaluation count : 2818068 in 6 samples of 469678 calls.
;             Execution time mean : 217,315117 ns
;    Execution time std-deviation : 4,662882 ns
;   Execution time lower quantile : 212,536595 ns ( 2,5%)
;   Execution time upper quantile : 221,615209 ns (97,5%)

;CLJS (dotimes [_ 10000]) Elapsed time: 3.111300 msecs
