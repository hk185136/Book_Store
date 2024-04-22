export const urls = {
    book : {
        get : 'http://localhost:8080/api/user/books/',
        getByTitle : 'http://localhost:8080/api/user/books/title/',
        getByAuthor : 'http://localhost:8080/api/user/books/author/',
        getByGenre : 'http://localhost:8080/api/user/books/genre/',
        getByPrice : 'http://localhost:8080/api/user/books/price/',
        getByPriceLessOrEqual : 'http://localhost:8080/api/user/books/lessorequalprice/',
        getByPriceRange : 'http://localhost:8080/api/user/books/pricerange/',
        addBook : 'http://localhost:8080/api/admin/books/',
        deleteBook : 'http://localhost:8080/api/admin/books/',
        updateBook : 'http://localhost:8080/api/admin/books/'
    },
    auth : {
        signin : 'http://localhost:8080/api/auth/signin/',
        signup : 'http://localhost:8080/api/auth/signup'
    },
    user : {
        editUser : 'http://localhost:8080/api/auth/editUser/',
        getAllUsers : 'http://localhost:8080/api/getAllUsers'
    },
    cart : {
        get : 'http://localhost:8080/api/item/getItem/',
        addToCart : 'http://localhost:8080/api/item/addToCart',
        removeFromCart : 'http://localhost:8080/api/item/',
        getCartItemsByUser : 'http://localhost:8080/api/item/searchByStatus/Added to cart',
        ChangeQty : 'http://localhost:8080/api/item/'
    },
    order : {
        getOrdersByUser : 'http://localhost:8080/api/item/getOrders',
        addOrder : 'http://localhost:8080/api/item/addToOrder',
        cancelOrder : 'http://localhost:8080/api/item/updateStatus/Cancelled',
        removeOrder : 'http://localhost:8080/api/item/'
    },
    notification : {
        getNotificationsByUser : 'http://localhost:8080/api/user/notification/',
        deleteNotification : 'http://localhost:8080/api/user/notification/deleteNotification/',
        deleteNotificationsByUser : 'http://localhost:8080/api/user/notification/deleteNotifications/',
        dispatchStockRefill : 'http://localhost:8080/api/user/notification/dispatchBookStockRefillNotfications',
        dispatchStatusUpdate : 'http://localhost:8080/api/user/notification/dispatchOrderStatusNotfications'
    },
    subscription : {
        enableSubscription : 'http://localhost:8080/api/user/subscription/enableSubscription/',
        addSubscription : 'http://localhost:8080/api/user/subscription/addSubscription',
        getSubscriptionsByUser : 'http://localhost:8080/api/user/subscription/getSubscriptions/'
    },
    history : {
        getHistoryByUser : 'http://localhost:8080/api/user/orderHistory/getOrderHistory/',
        deleteHistory : 'http://localhost:8080/api/user/orderHistory/',
        emptyHistory : 'http://localhost:8080/api/user/orderHistory/delete/'
    }
}