<?php
include __DIR__ . '/vendor/autoload.php';
use Illuminate\Database\Capsule\Manager as Capsule;

$capsule = new Capsule;

$capsule->addConnection([
    'driver'    => 'mysql',
    'host'      => 'localhost',
    'database'  => 'email_send_system',
    'username'  => 'root',
    'password'  => 'cwj727834048',
    'charset'   => 'utf8',
    'collation' => 'utf8_unicode_ci',
    'prefix'    => '',
]);

// Set the event dispatcher used by Eloquent models... (optional)

// Make this Capsule instance available globally via static methods... (optional)
$capsule->setAsGlobal();

// Setup the Eloquent ORM... (optional; unless you've used setEventDispatcher())
$capsule->bootEloquent();
//$name="陈伟杰";
//$users = Capsule::table('user')->where('name', '=',$name)->orwhere('password','=','12345')->get();
//echo json_encode($users);
?>