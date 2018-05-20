<?php
include_once("taskView.php");
$task=Task::all();
echo json_encode($task);
?>