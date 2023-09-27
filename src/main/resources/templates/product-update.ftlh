<#import "blocks/template.ftlh" as t>
<@t.template user=user!{}>
<#--<@t.template user>-->
<h1 style="text-align: center">My property</h1>
    <hr>
    <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
       data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="true" aria-controls="collapseExample">
        Edit Object
    </a>
    <div class="collapse mb-3" id="collapseExample" style="">
        <form action="/product/update/${product.id}" method="post" enctype="multipart/form-data">
            <#--<form action="/product/edit/${product.id}" method="post" enctype="multipart/form-data">-->
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <label for="productName"> Property Name</label>
                <input type="text" required class="form-control"
                       id="productName" name="title" value="${product.title}">
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <div class="input-group mb-3">
                    <input type="number" required class="form-control" id="price" name="price" value="${product.price}">
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
                <textarea required class="form-control" id="description"
                          name="description">${product.description}</textarea>
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


            <#--<input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <button type="submit" style="width: 100%" class="btn btn-dark">Save Changes</button>
        </form>
    </div>
</@t.template>


