(ns readme.usage.insert-at
  (:require [strojure.vectops.core :as vec]))

(vec/insert-at [1 2 3 4 5] 0 :x)
#_=> [:x 1 2 3 4 5]

(vec/insert-at [1 2 3 4 5] 2 :x)
#_=> [1 2 :x 3 4 5]

(vec/insert-at [1 2 3 4 5] 5 :x)
#_=> [1 2 3 4 5 :x]

(vec/insert-at [1 2 3 4 5] 6 :x)
;; Execution error (IndexOutOfBoundsException)

(vec/insert-at nil 0 :x)
#_=> [:x]
