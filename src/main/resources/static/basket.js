$(document).ready(function() {
    // Sepeti getiren AJAX isteği
    $.ajax({
        type: "GET",
        url: "/basket",
        success: function(response) {
            // Sepet öğelerini listele
            for (var i = 0; i < response.products.length; i++) {
                var product = response.products[i];
                var productElement = `
                    <div class="basket-item">
                        <h3>${product.name}</h3>
                        <p>Price: ${product.price}</p>
                    </div>
                `;
                $("#basketItems").append(productElement);
            }
            // Toplam tutarı göster
            $("#basketTotal").text("Total Amount: " + response.totalAmount);
        }
    });

    // Sepete ekleme işlemi
    function addToBasket(productId) {
        $.ajax({
            type: "POST",
            url: "/basket/add",
            data: JSON.stringify({ "id": productId }),
            contentType: "application/json",
            success: function(response) {
                alert("Product added to basket!");
                // Yeniden yüklemek yerine, sepeti güncelleyebilirsiniz
                var productElement = `
                    <div class="basket-item">
                        <h3>${response.name}</h3>
                        <p>Price: ${response.price}</p>
                    </div>
                `;
                $("#basketItems").append(productElement);
                // Toplam tutarı güncelle
                $("#basketTotal").text("Total Amount: " + response.totalAmount);
            }
        });
    }

    // Sepete ekleme butonlarını dinamik olarak bağlayın
    $(document).on("click", ".add-to-basket-btn", function() {
        var productId = $(this).data("product-id");
        addToBasket(productId);
    });

    // Sepetten kaldırma işlemi
    $(document).on("click", ".remove-from-basket-btn", function() {
        var productId = $(this).data("product-id");
        $.ajax({
            type: "POST",
            url: "/basket/remove",
            data: JSON.stringify({ "id": productId }),
            contentType: "application/json",
            success: function(response) {
                alert("Product removed from basket!");
                // Yeniden yüklemek yerine, sepeti güncelleyebilirsiniz
                var basketItems = response.products;
                $("#basketItems").empty();
                for (var i = 0; i < basketItems.length; i++) {
                    var product = basketItems[i];
                    var productElement = `
                        <div class="basket-item">
                            <h3>${product.name}</h3>
                            <p>Price: ${product.price}</p>
                        </div>
                    `;
                    $("#basketItems").append(productElement);
                }
                // Toplam tutarı güncelle
                $("#basketTotal").text("Total Amount: " + response.totalAmount);
            }
        });
    });

    // Checkout işlemi
    $(document).on("click", "#checkoutBtn", function() {
        $.ajax({
            type: "POST",
            url: "/basket/purchase",
            success: function(response) {
                alert("Purchase completed successfully!");
                // Sepeti temizle
                $("#basketItems").empty();
                $("#basketTotal").text("");
            }
        });
    });
});
