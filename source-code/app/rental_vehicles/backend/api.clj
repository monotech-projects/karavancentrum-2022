
(ns app.rental-vehicles.backend.api
    (:require [app.rental-vehicles.backend.editor.mutations]
              [app.rental-vehicles.backend.editor.resolvers]
              [app.rental-vehicles.backend.editor.lifecycles]
              [app.rental-vehicles.backend.lister.mutations]
              [app.rental-vehicles.backend.lister.resolvers]
              [app.rental-vehicles.backend.lister.lifecycles]
              [app.rental-vehicles.backend.viewer.mutations]
              [app.rental-vehicles.backend.viewer.resolvers]
              [app.rental-vehicles.backend.viewer.lifecycles]
              [app.rental-vehicles.backend.handler.helpers :as handler.helpers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.rental-vehicles.backend.handler.helpers
(def get-rental-vehicles handler.helpers/get-rental-vehicles)
