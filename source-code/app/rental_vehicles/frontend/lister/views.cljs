
(ns app.rental-vehicles.frontend.lister.views
    (:require [app.common.frontend.api :as common]
              [elements.api            :as elements]
              [engines.item-lister.api :as item-lister]
              [layouts.surface-a.api   :as surface-a]
              [re-frame.api            :as r]

              ; TEMP
              [plugins.dnd-kit.api :as dnd-kit]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :rental-vehicles.lister])]
          [common/item-lister-download-info :rental-vehicles.lister {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-item-structure
  [lister-id item-dex {:keys [modified-at name thumbnail]} dnd-kit-props]
  (let [timestamp  @(r/subscribe [:activities/get-actual-timestamp modified-at])
        item-last? @(r/subscribe [:item-lister/item-last? lister-id item-dex])]
       [common/list-item-structure {:cells [[common/list-item-drag-handle {:indent {:left :xs}} dnd-kit-props]
                                            [common/list-item-thumbnail    {:thumbnail (:media/uri thumbnail)}]
                                            [common/list-item-primary-cell {:label name :stretch? true :placeholder :unnamed-vehicle}]
                                            [common/list-item-detail       {:content timestamp :width "160px"}]
                                            [common/list-item-marker       {:icon :navigate_next}]]
                                    :separator (if-not item-last? :bottom)}]))

(defn vehicle-item
  [lister-id item-dex {:keys [id] :as item} {:keys [isDragging] :as dnd-kit-props}]
  [elements/toggle {:background-color (if isDragging :highlight)
                    :content          [vehicle-item-structure lister-id item-dex item dnd-kit-props]
                    :hover-color      :highlight
                    :on-click         [:router/go-to! (str "/@app-home/rental-vehicles/"id)]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-list
  [_ items]
  [dnd-kit/body :rental-vehicles.lister
                {:items            items
                 :item-id-f        :id
                 :item-element     #'vehicle-item
                 :on-order-changed [:item-lister/reorder-items! :rental-vehicles.lister]}])

(defn- vehicle-lister-body
  []
  [item-lister/body :rental-vehicles.lister
                    {:default-order-by :order/descending
                     :order-key        :order
                     :items-path       [:rental-vehicles :lister/downloaded-items]
                     :error-element    [common/error-content {:error :the-content-you-opened-may-be-broken}]
                     :ghost-element    #'common/item-lister-ghost-element
                     :list-element     #'vehicle-list}])

(defn- vehicle-lister-header
  []
  [common/item-lister-header :rental-vehicles.lister
                             {:cells [[common/item-lister-header-spacer :rental-vehicles.lister {:width "144px"}]
                                      [common/item-lister-header-cell   :rental-vehicles.lister {:label :name :stretch? true}]
                                      [common/item-lister-header-cell   :rental-vehicles.lister {:label :last-modified :width "160px"}]
                                      [common/item-lister-header-spacer :rental-vehicles.lister
                                                                        {:width "36px"}]]}])

(defn- body
  []
  [common/item-lister-wrapper :rental-vehicles.lister
                              {:body   #'vehicle-lister-body
                               :header #'vehicle-lister-header}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])
        create-vehicle-uri (str "/@app-home/rental-vehicles/create")]
       [common/item-lister-create-item-button :rental-vehicles.lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-vehicle-uri}]))

(defn- search-field
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/item-lister-search-field :rental-vehicles.lister
                                        {:disabled?   lister-disabled?
                                         :placeholder :search-in-rental-vehicles
                                         :search-keys [:name]}]))

(defn- search-description
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/item-lister-search-description :rental-vehicles.lister
                                              {:disabled? lister-disabled?}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/surface-breadcrumbs :rental-vehicles.lister/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :rental-vehicles}]
                                    :disabled? lister-disabled?}]))

(defn- label
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/surface-label :rental-vehicles.lister/view
                             {:disabled? lister-disabled?
                              :label     :rental-vehicles}]))

(defn- header
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :rental-vehicles.lister])]
          [:<> [:div {:style {:display "flex" :justify-content "space-between" :flex-wrap "wrap" :grid-row-gap "24px"}}
                     [:div [label]
                           [breadcrumbs]]
                     [:div [create-item-button]]]
               [search-field]
               [search-description]]
          [common/item-lister-ghost-header :rental-vehicles.lister {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [header]
       [body]
       [footer]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
