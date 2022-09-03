(ns strojure.vectops.core-test
  (:require #?(:clj  [clojure.test :as test :refer [deftest testing]]
               :cljs [cljs.test :as test :refer-macros [deftest testing]])
            [strojure.vectops.core :as vec])
  #?(:clj (:import (clojure.lang ITransientCollection))))

#?(:clj  (set! *warn-on-reflection* true)
   :cljs (set! *warn-on-infer* true))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(comment
  (test/run-tests))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(declare thrown?)

(def ^:private x 'x)

(def ^:private transient?
  (partial instance? #?(:clj ITransientCollection :cljs TransientVector)))

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest insert-at-test

  (testing "Insert element at index."
    (test/are [form] form
      (vector? (vec/insert-at [1 2 3 4 5] 2 x))
      (= [1 2 x 3 4 5] (vec/insert-at [1 2 3 4 5] 2 x))
      (= [x 1 2 3 4 5] (vec/insert-at [1 2 3 4 5] 0 x))
      (= [1 2 3 4 5 x] (vec/insert-at [1 2 3 4 5] 5 x))
      (= [x],,,,,,,,,, (vec/insert-at [] 0 x))
      (= [x],,,,,,,,,, (vec/insert-at nil 0 x))))

  (testing "Insert element out of bounds."
    (test/are [form] form
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at [1 2 3 4 5] -1 x))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at [1 2 3 4 5] 6 x))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at nil 1 x))))

  (testing "Preserve metadata on insert."
    (test/are [form] form
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 2 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 0 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [1 2 3 4 5] 5 x)))
      (= {::meta true} (meta (vec/insert-at ^::meta [] 0 x)))))

  (testing "Insert element in transient vector."
    (test/are [form] form
      (transient? (vec/insert-at (transient [1 2 3 4 5]) 2 x))
      (= [1 2 x 3 4 5] (persistent! (vec/insert-at (transient [1 2 3 4 5]) 2 x)))
      (= [x 1 2 3 4 5] (persistent! (vec/insert-at (transient [1 2 3 4 5]) 0 x)))
      (= [1 2 3 4 5 x] (persistent! (vec/insert-at (transient [1 2 3 4 5]) 5 x)))
      (= [x],,,,,,,,,, (persistent! (vec/insert-at (transient []) 0 x)))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at (transient [1 2 3 4 5]) -1 x))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at (transient [1 2 3 4 5]) 6 x))))

  (testing "Insert element in non-editable collection."
    (test/are [form] form
      (= [1 2 x 3 4 5] (vec/insert-at (subvec [0 1 2 3 4 5] 1) 2 x))
      (= [x 1 2 3 4 5] (vec/insert-at (subvec [0 1 2 3 4 5] 1) 0 x))
      (= [1 2 3 4 5 x] (vec/insert-at (subvec [0 1 2 3 4 5] 1) 5 x))
      (= {::meta true} (meta (with-meta (vec/insert-at (subvec [0 1 2 3 4 5] 1) 2 x) {::meta true})))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at (subvec [0 1 2 3 4 5] 1) -1 x))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/insert-at (subvec [0 1 2 3 4 5] 1) 6 x))))
  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

(deftest remove-at-test

  (testing "Remove element at index."
    (test/are [form] form
      (vector? (vec/remove-at [1 2 3 4 5] 2))
      (= [2 3 4 5] (vec/remove-at [1 2 3 4 5] 0))
      (= [1 2 3 4] (vec/remove-at [1 2 3 4 5] 4))
      (= [1 2 4 5] (vec/remove-at [1 2 3 4 5] 2))
      (= [],,,,,,, (vec/remove-at [1] 0))))

  (testing "Remove element out of bounds."
    (test/are [form] form
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/remove-at [1 2 3 4 5] -1))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/remove-at [1 2 3 4 5] 5))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/remove-at nil 0))))

  (testing "Preserve metadata on remove."
    (test/are [form] form
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 0)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 4)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1 2 3 4 5] 2)))
      (= {::meta true} (meta (vec/remove-at ^::meta [1] 0)))))

  (testing "Remove element in transient vector."
    (test/are [form] form
      (transient? (vec/remove-at (transient [1 2 3 4 5]) 2))
      (= [2 3 4 5] (persistent! (vec/remove-at (transient [1 2 3 4 5]) 0)))
      (= [1 2 3 4] (persistent! (vec/remove-at (transient [1 2 3 4 5]) 4)))
      (= [1 2 4 5] (persistent! (vec/remove-at (transient [1 2 3 4 5]) 2)))
      (= [],,,,,,, (persistent! (vec/remove-at (transient [1]) 0)))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/remove-at (transient [1 2 3 4 5]) -1))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/remove-at (transient [1 2 3 4 5]) 5))))

  (testing "Remove element in non-editable collection."
    (test/are [form] form
      (= [2 3 4 5] (vec/remove-at (subvec [0 1 2 3 4 5] 1) 0))
      (= [1 2 3 4] (vec/remove-at (subvec [0 1 2 3 4 5] 1) 4))
      (= [1 2 4 5] (vec/remove-at (subvec [0 1 2 3 4 5] 1) 2))
      (= {::meta true} (meta (with-meta (vec/remove-at [1 2 3 4 5] 2) {::meta true})))
      ;; Removing in subvec at -1 does not throw exceptions in CLJ
      #?(:clj  (thrown? IndexOutOfBoundsException (vec/remove-at (subvec [0 1 2 3 4 5] 1) -2))
         :cljs (thrown? js/Error,,,,,,,,,,,,,,,,, (vec/remove-at (subvec [0 1 2 3 4 5] 1) -1)))
      (thrown? #?(:clj IndexOutOfBoundsException :cljs js/Error) (vec/remove-at (subvec [0 1 2 3 4 5] 1) 5))))
  )

;;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
