<#import "blocks/template.ftlh" as t>
<@t.template user=user>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        h1 {
            color: #FF5733;
        }
        p {
            font-size: 18px;
            margin-top: 20px;
        }
    </style>
    </head>
    <body>
    <#if errorType?has_content>
        <#if errorType == "UserNotFound">
            <h1>User Not Found</h1>
            <p>${errorMessage}</p>
        <#elseif errorType == "ProductNotFound">
            <h1>Product Not Found</h1>
            <p>${errorMessage}</p>
        <#elseif errorType == "IOError">
            <h1>Input/Output Error</h1>
            <p>${errorMessage}</p>
        <#else>
            <h1>An Error Occurred</h1>
            <p>Sorry, an error occurred while processing your request. Please try again later.</p>
        </#if>
    <#else>
        <h1>An Error Occurred</h1>
        <p>Sorry, an error occurred while processing your request. Please try again later.</p>
    </#if>
    </body>
</@t.template>

