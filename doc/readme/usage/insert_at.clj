(ns readme.usage.insert-at
  (:require [strojure.vectops.core :as vec]))

(vec/insert-at [0 1 2 3 4] 0 :x)
#_=> [:x 0 1 2 3 4]

(vec/insert-at [0 1 2 3 4] 2 :x)
#_=> [0 1 :x 2 3 4]

(vec/insert-at [0 1 2 3 4] 5 :x)
#_=> [0 1 2 3 4 :x]

(vec/insert-at [0 1 2 3 4] 6 :x)
;; Execution error (IndexOutOfBoundsException)

(vec/insert-at nil 0 :x)
#_=> [:x]
