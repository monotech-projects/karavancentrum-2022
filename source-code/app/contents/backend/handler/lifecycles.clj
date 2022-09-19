
(ns app.contents.backend.handler.lifecycles
    (:require [app.contents.backend.handler.routes :as handler.routes]
              [x.server-core.api                   :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-init [:router/add-route! :contents/download-content
                                       {:route-template "/contents/:content-id"
                                        :get {:handler handler.routes/download-content}}]})