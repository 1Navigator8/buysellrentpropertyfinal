<#import "blocks/template.ftlh" as t>
<@t.template user=user!{}>
<#-- <@t.template user>-->
    <h1 style="text-align: center">My property</h1>
    <hr>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="true" aria-controls="collapseExample">
        Add Object
    </a>
    <div class="collapse mb-3" id="collapseExample" style="">
        <form action="/product/create" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="productName"> Property Name</label>
                <input type="text" required class="form-control"
                       id="productName" name="title">
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <div class="input-group mb-3">
                    <input type="number" required class="form-control" id="price" name="price">
                    <div class="input-group-append">
                        <span class="input-group-text">€</span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="searchCity">City</label>
                <select id="searchCity" required name="city" class="form-control">
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
            <div class="form-group">
                <label for="description">Description</label>
                <textarea required class="form-control" id="description" name="description" maxlength="3000"></textarea>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input required type="file" name="file1" id="file1">
                    <label class="custom-file-label" for="file1">Photo1</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file2" id="file2">
                    <label class="custom-file-label" for="file2">Photo2</label>
                </div>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file3" id="file3">
                    <label class="custom-file-label" for="file3">Photo3</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit" style="width: 100%" class="btn btn-dark">Add</button>
        </form>
    </div>

    <div class="card-columns">
        <#list products as product>
            <a href="/product/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <div class="card-top d-flex justify-content-between">
                        <form action="/product/delete/${product.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger m-3">
                                Delete
                            </button>
                        </form>
                        <form action="/product/update/${product.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-primary m-3">
                                Edit
                            </button>
                        </form>
                    </div>
                    <img src="/images/${product.previewImageId}"
                         class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text">${product.price} €</p>
                    </div>
                    <div class="card-footer">
                        ${product.city}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Not found</h1>
        </#list>
    </div>
</@t.template>
