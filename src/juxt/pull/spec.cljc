(ns juxt.pull.spec
  "Spec for pull API."
  (:require
   [clojure.spec.alpha :as s]
   [juxt.pull.core :as core]
   [juxt.pull.protocol :as p]))

(s/def ::key (s/or :keyword keyword? :string string?))
(s/def ::pattern
  (s/coll-of (s/or :attr (s/or :keyword ::key :wildcard #{'*})
                   :join (s/map-of ::key ::pattern))))

(s/def ::target (s/nilable #(satisfies? p/Findable %)))

(s/def ::result ::target)

(s/def ::no-wildcard? boolean?)
(s/def ::no-ref? boolean?)
(s/def ::stealth (s/coll-of ::key))
(s/def ::shadow (s/map-of ::key fn?))
(s/def ::pull-opts (s/keys :opt-un [::no-wildcard? ::stealth ::shadow ::no-ref?]))

;; API
(s/fdef core/pull
  :args (s/cat :target ::target :pattern ::pattern :opts (s/? ::pull-opts))
  :ret ::result)

(comment
  (s/valid? ::pattern [:a])
  (s/valid? ::pattern [:a {:b '[*]}])
  (s/conform ::pattern [:a/a {:b/a [:c/a]}])
  (s/conform ::pattern [:b]))
