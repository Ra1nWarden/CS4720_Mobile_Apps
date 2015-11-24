var express = require('express');
var app = express();
var port = 8080;
var router = express.Router();

router.get('/privacy', function(req, res) {
  res.json({
    success: true,
    content: "This app does not save any user data! :)"
  });
});

app.use('/', router);
app.listen(port);
