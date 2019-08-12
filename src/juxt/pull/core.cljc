(ns juxt.pull.core
  (:require
   [juxt.pull.core.impl :as impl]))

(defn pull
  "Take a query, and some state. Return a map.
  Optionally opts is map, which can have kv pairs:

   - :shadow a shadow attributes map
   - :stealth a stealth attributes set
   - :no-wildcard? wildcard will be ignored

  the shadow attributes map defines attributes not really exists,
  but can be calculated by a function takes a single argument (parent data),
  returns a value as the attribute value.

  all key inside stealth attributes set will not appeared in the
  returned data as if non-exists, perfectly for sensitive data."
  ([data query]
   (pull data query {}))
  ([data query opts]
   (impl/pull data data query 
              (update opts :stealth
                      (fn [value]
                        (if value (set value) #{}))))))
