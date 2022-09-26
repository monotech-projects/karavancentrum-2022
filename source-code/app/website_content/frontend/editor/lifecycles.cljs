
(ns app.website-content.frontend.editor.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:home/add-menu-item! {:group    :website
                                       :icon     :wysiwyg
                                       :label    :website-content
                                       :on-click [:router/go-to! "/@app-home/website-content"]
                                       :horizontal-weight 1}]})