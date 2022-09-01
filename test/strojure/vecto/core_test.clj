(ns strojure.vecto.core-test
  (:require [clojure.test :as test :refer [deftest testing]]
            [strojure.vecto.core :as vec]))

(set! *warn-on-reflection* true)

(declare thrown?)

(def ^:private x 'x)

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest insert-test

  (testing "Insert element at index."
    (test/are [form] form
      (vector?,,,,,,,, (vec/insert [1 2 3 4 5] 2 x))
      (= [1 2 x 3 4 5] (vec/insert [1 2 3 4 5] 2 x))
      (= [x 1 2 3 4 5] (vec/insert [1 2 3 4 5] 0 x))
      (= [1 2 3 4 5 x] (vec/insert [1 2 3 4 5] 5 x))
      (= [x],,,,,,,,,, (vec/insert [] 0 x))))

  (testing "Insert element out of bounds."
    (test/are [form] form
      (thrown? IndexOutOfBoundsException (vec/insert [1 2 3 4 5] -1 x))
      (thrown? IndexOutOfBoundsException (vec/insert [1 2 3 4 5] 6 x))))

  (testing "Preserve metadata on insert."
    (test/are [form] (::meta (meta form))
      (vec/insert ^::meta [1 2 3 4 5] 2 x)
      (vec/insert ^::meta [1 2 3 4 5] 2 x)
      (vec/insert ^::meta [1 2 3 4 5] 0 x)
      (vec/insert ^::meta [1 2 3 4 5] 5 x)
      (vec/insert ^::meta [] 0 x)))

  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
