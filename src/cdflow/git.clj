(ns cdflow.git
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clj-jgit.porcelain :as git]))

(defn get-branch-name-from-ref [ref]
  (str/replace-first (.getName ref) "refs/heads/" ""))

;(defn get-branches-as-tree [repo]
;  (let [branches (branch-list repo)]))

(defn branch-list [repo]
  (map get-branch-name-from-ref (git/git-branch-list (git/load-repo repo))))

(def branches '("development" "feature/prova" "feature/test" "master" "feature/subfeature/casa"))

;(vector->adj ["root" "feature" "prova"] [])

(def a '(("root" "development") ("root" "feature") ("feature" "prova") ("feature" "prova2") ("feature" "test") ("test" "prova3")))

(defn ->branch
  [id kids]
  (conj kids id))

(defn ->leaf
  [id]
  (list id))

(defn descendant
  [adj-list node]
  (seq (map second (filter #(= (first %) node) adj-list))))

(defn ->tree
  [adj-list node]
  (let [->tree' (partial ->tree adj-list)]
    (if-let [kid-ids (descendant adj-list node)]
      (->branch node (map ->tree' kid-ids))
      (->leaf node))))

(defn ->adj [vec adj]
  (if (= 2(count vec))
    (conj adj (lazy-seq vec))
    (->adj (next vec) (conj adj (take 2 vec)))))

(def branches ["root/master" "root/development" "root/feature/prova" "root/feature/prova2" "root/feature/subfeatures/prova3"])

(let [vec-branches (map #(str/split % #"/") branches)
      adj-branches (map #(->adj % []) vec-branches)
      adj-list (distinct(apply concat adj-branches))]
  (->tree adj-list "root"))



;"root/development" [["root" "development"]]
;"root/feature/prova" [["root" "feature"] ["feature" "prova"]]