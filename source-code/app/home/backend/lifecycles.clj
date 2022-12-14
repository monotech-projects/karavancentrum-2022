
(ns app.home.backend.lifecycles
    (:require [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:router/add-route! :admin/home
                                       {:core-js        "app.js"
                                        :route-template "/@app-home"
                                        :client-event   [:home/load!]
                                        :restricted?    true}]})
