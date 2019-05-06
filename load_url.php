<?php
    include_once "dbconnect.php";

    $sql = "SELECT url FROM image";

    $result = $connect->query($sql);

    $data;

    if($result->num_rows > 0){
        while($rows = $result->fetch_assoc()){
            $data = $rows["url"];
        }
    }

    echo "<script>console.log(".$data.")</script>";


    
?>