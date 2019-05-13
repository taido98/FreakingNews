<?php
    include_once "dbconnect.php";

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $idUser = (int) $_GET["idUser"];
        $data;
        $sql = "SELECT * FROM users WHERE id = $idUser";

        $result = $connect->query($sql);

        if(!$result)
            die($connect->error);
        else if($result->num_rows > 0){
            while($rows = $result->fetch_assoc()){
                $data[] = $rows;
            }
        }
        echo json_encode($data);
    }
?>