# vectops

Basic operations with Clojure(Script) vectors.

[![Clojars Project](https://img.shields.io/clojars/v/com.github.strojure/vectops.svg)](https://clojars.org/com.github.strojure/vectops)

## Purpose

* Provide functions for basic operations with persistent vectors like inserting
  or removing elements at index.
* Performant implementation without extra dependencies.
* Support Clojure and ClojureScript.

## Usage

### insert-at

The function `insert-at` inserts element in persistent vector at specified
index. There is also `insert-at!` function for transient vectors.

```clojure
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
```

### remove-at

The function `remove-at` removes element at specified index from persistent
vector. There is also `remove-at!` function for transient vectors.

```clojure
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
```
