<#import "blocks/template.ftlh" as t>
    <@t.template user=user!{}>
    <#--<@t.template user>-->
    <h1 style="text-align: center">AUTHORIZATION</h1>
    <hr>
    <form action="/login " method="post">
        <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="email" name="username">
            <div id="email" class="invalid-feedback">
                *Incorrect email address*
            </div>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password" name="password">
            <div id="password" class="invalid-feedback">
                *Incorrect password*
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">LOG IN</button>
    </form>
    <hr>
    <h2>Not a member? Register <a href="/registration">SIGN UP</a></h2>
</@t.template>
