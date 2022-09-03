(ns readme.usage.remove-at
  (:require [strojure.vectops.core :as vec]))

(vec/remove-at [1 2 3 4 5] 0)
#_=> [2 3 4 5]

(vec/remove-at [1 2 3 4 5] 2)
#_=> [1 2 4 5]

(vec/remove-at [1 2 3 4 5] 4)
#_=> [1 2 3 4]

(vec/remove-at [1 2 3 4 5] 5)
;; Execution error (IndexOutOfBoundsException)
