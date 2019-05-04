<?php 
    include_once "dbconnect.php";

    $idPost = $_GET["idPost"];
    $idUser = $_GET["idUser"];
    $type = $_GET["type"];
    $content = $_GET["content"];
    // $idPost = 1;

    if($type == "loadCmt"){
        $sql = "SELECT c.*, u.name, u.url_avatar FROM comments c
        JOIN users u ON c.idUser = u.id
        WHERE idPost = $idPost
        ORDER BY c.created_at ASC";
        $result = $connect->query($sql);
        $data;

        if(!$result){
            die($connect->error);
        }
        if($result->num_rows > 0){
            while($rows = $result->fetch_assoc()){
                $data[] = $rows;
            }
        }
        echo json_encode($data);
    }
    if($type == "addCmt"){
        $sql = "INSERT INTO comments(idPost, idUser, content) 
        VALUES($idPost,$idUser,'$content')";

        $result = $connect->query($sql);

        if(!$result){
            die("Lỗi" + $connect->error);
        }
        echo "Success";
    }
?>