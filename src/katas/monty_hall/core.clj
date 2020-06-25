(ns katas.monty-hall.core)

(def doors [:car :goat :goat])

;; Randomize car goat gaot
(defn new-game []
  (shuffle doors))

;; Randomize user selection
(defn get-user-selection [choices]
  (nth choices (rand-int (count choices))))

;; Configure whether the user will switch doors and get result
(defn wins? [switch?]
  (let [game        (new-game)
        user-door   (get-user-selection game)
        left        (->
                      ;; The host will always remove a goat,
                      ;; set to remove the extra goat (lazy soln)
                      (set game)
                      ;; Remove the user's choice, order doesn't matter here
                      ;; because user's choice is already known
                      (disj user-door)
                      (first))]
    (if switch?
      (= :car left)
      (= :car user-door))))

;; Play game x times and report the # of times won
(defn games-won [times switch?]
  ;; maybe there's a better function than reduce
  (reduce (fn [result _]
            (if (wins? switch?)
              (inc result)
              result))
          0
          (range times)))

;; Report results of monty hall
(defn simulate []
  (let [times 1000
        switch-wins (games-won times true)
        stay-wins (games-won times false)]

    {:switch {:win (/ switch-wins times)
              :lose (/ (- times switch-wins) times)}
     :stay {:win (/ stay-wins times)
            :lose (/ (- times stay-wins) times)}}))

;; hmm +- ~%4 around expected for 1000,
;; ~%2 around expected for 10000?
(simulate)
