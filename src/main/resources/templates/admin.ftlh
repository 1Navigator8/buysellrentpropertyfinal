<#import "blocks/template.ftlh" as t>
<@t.template user=user!{}>
    `<h1 style="text-align: center">Admin Dashboard</h1>
    <hr>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">User</th>
            <th scope="col">Email</th>
            <th scope="col">Phone number</th>
            <th scope="col">Activity</th>
            <th scope="col">Roles</th>
            <th scope="col">Detailed information</th>
            <th scope="col">Edit</th>
            <th scope="col">Ban</th>
            <th scope="col">Delete</th>
        </tr>
        <#list users as user>
            <tr>
                <th>${user.name}</th>
                <th>${user.email}</th>
                <th>${user.phoneNumber}</th>
                <th>
                    <#if user.active>
                        <span class="text-success">true</span>
                    <#else>
                        <span class="text-danger">false</span>
                    </#if>
                </th>
                <th><#list user.roles as role>${role}<#sep>, </#list></th>

                <th><a href="/user/${user.id}">Detailed information</a></th>
                <th><a href="/admin/user/edit/${user.id}">Editors</a></th>
                <th>
                    <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#banModal${user.id}">
                        BAN
                    </button>
                </th>

                <th>
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                            data-target="#deleteModal${user.id}">
                        DELETE
                    </button>
                </th>
            </tr>
            <!-- Модальное окно для подтверждения Ban -->
            <div class="modal fade" id="banModal${user.id}" tabindex="-1" role="dialog" aria-labelledby="banModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="banModalLabel">Ban</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to perform this action?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <form action="/admin/user/ban/${user.id}" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <button type="submit" class="btn btn-warning">OK</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Модальное окно для подтверждения удаления -->
            <div class="modal fade" id="deleteModal${user.id}" tabindex="-1" role="dialog"
                 aria-labelledby="deleteModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to delete this user? This action cannot be undone.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <form action="/admin/user/delete/${user.id}" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </table>
    <#if users?size == 0>
        <h3>No users</h3>
    </#if>
</@t.template>


