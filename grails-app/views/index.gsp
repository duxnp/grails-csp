<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <style nonce="${perRequestNonce()}">
        .style-test {
            color: red;
        }
    </style>
</head>
<body>

<div id="content" role="main">
    <div class="container">
        <section class="row colset-2-its">
            <h1 class="style-test d-none" data-test-id="welcome-header">Welcome to Grails CSP</h1>

            <ul>
                <li>The above header should be visible if the inline script nonce worked.</li>
                <li>The above header should be red if the inline style nonce worked.</li>
            </ul>
        </section>
    </div>
</div>

<script nonce="${perRequestNonce()}">
    let welcomeHeader = document.querySelector('[data-test-id="welcome-header"]');
    welcomeHeader.classList.remove("d-none");
</script>

</body>
</html>
