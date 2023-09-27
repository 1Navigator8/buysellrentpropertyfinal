<#import "blocks/template.ftlh" as t>
<@t.template user=userByPrincipal!{}>
<#--<@t.template userByPrincipal>-->
    <h1 style="text-align: center">User information</h1>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.png"
             style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h2>${user.name}</h2>
        <br>
        <br>
        <h2>${user.email}</h2>
        <br>
        Phone number: <b>${user.phoneNumber}</b>
    </div>
    <br>
    <hr>
    <h1 style="text-align: center">User Objects ${user.name}</h1>
    <br>
    <div class="card-columns">
        <#list products as product>
            <a href="/product/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product"style="height: 400px;">
                    <img src="/images/${product.previewImageId}"
                         class="card-img-top" style="width: 100%; height: 70%; object-fit: cover;">

                    <div class="card-body d-flex flex-column"  style="flex: 30%; padding: 0.5rem;">
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text">${product.price} €</p>
                    </div>
                    <div class="card-footer" style="flex: 10%; padding: 0.5rem;">
                        ${product.city}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Not found</h1>
        </#list>
    </div>
</@t.template>
