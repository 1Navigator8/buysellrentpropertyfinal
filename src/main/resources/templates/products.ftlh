<#import "blocks/template.ftlh" as t>
<@t.template user=user!{}>
<#--<@t.template user>-->
    <h1 style="text-align: center">Property search</h1>
    <hr>
    <form method="get" action="/" class="mb-4">
        <div class="form-row">
            <div class="form-group col-md-2">
                <label for="searchCity">City</label>
                <select id="searchCity" class="form-control">
                    <option value="">City not selected</option>
                    <option value="Berlin">Berlin</option>
                    <option value="Hamburg">Hamburg</option>
                    <option value="München">München</option>
                    <option value="Frankfurt">Frankfurt</option>
                    <option value="Köln">Köln</option>
                    <option value="Leipzig">Leipzig</option>
                    <option value="Dresden">Dresden</option>
                    <option value="Darmstadt">Darmstadt</option>
                    <option value="Lübeck">Lübeck</option>
                    <option value="Würzburg">Würzburg</option>
                    <option value="Heidelberg">Heidelberg</option>
                    <option value="Stuttgart">Stuttgart</option>
                </select>
            </div>

            <div class="form-group col-md-10">
                <label for="searchWord">Search by ads</label>
                <div class="input-group">
                    <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                           id="searchWord">
                    <div class="input-group-append">
                        <button class="btn btn-dark" type="submit">Search</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <hr>
    <div class="card-columns">
        <#list products as product>
            <a href="/product/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product" style="height: 400px;"><#--<!!!!!!>-->
                    <img src="/images/${product.previewImageId}"
                         class="card-img-top" style="width: 100%; height: 70%; object-fit: cover;"><#--<!!!!!!>-->
                    <div class="card-body d-flex flex-column"  style="flex: 30%; padding: 0.5rem;"><#--<!!!!!!>-->
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
