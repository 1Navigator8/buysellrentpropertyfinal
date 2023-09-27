<#import "blocks/template.ftlh" as t>
<#--<@t.template user>-->
<@t.template user=user!{}>
    <h1 style="text-align: center">User edit ${user.name}</h1><hr>
    <form action="/admin/user/edit" method="post">
        <input type="text" name="username" value="${user.name}">
        <#list roles as role>
            <div>

                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-dark">Save</button>
    </form>

</@t.template>
