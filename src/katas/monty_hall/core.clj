(ns katas.monty-hall.core)

;; Prizes behind the doors
(def doors [:car :goat :goat])

(defn new-game
  "Randomize door prizes"
  []
  (shuffle doors))

(defn get-user-selection
  "Randomize user selection"
  [choices]
  (nth choices (rand-int (count choices))))

(defn wins?
  "Determines whether user will win with the strategy this game"
  [switch?]
  (let [game        (new-game)
        user-door   (get-user-selection game)
        door-left   (->
                      ;; The host will always remove a goat,
                      ;; set to remove the extra goat (lazy soln)
                      (set game)
                      ;; Remove the user's choice, order doesn't matter here
                      ;; because user's choice is already known
                      (disj user-door)
                      (first))]
    (if switch?
      (= :car door-left)
      (= :car user-door))))

(defn games-won
  "Play game x times and report the # of times won"
  [times switch?]
  (reduce (fn [result _]
            (if (wins? switch?)
              (inc result)
              result))
          0
          (range times)))

(defn simulate
  "Report results of monty hall"
  []
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
