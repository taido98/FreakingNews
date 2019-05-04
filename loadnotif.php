<?php
    include_once "../connectSQL.php";
    session_start();
    $iduser = $_SESSION["iduser"];
    $usertype = $_SESSION["usertype"];
    $type = $_GET["type"];

    if($type == "session"){
        $sql = "SELECT s.id,s.name, s.id_teacher, s.notify,
        (SELECT id_questioner FROM question 
        WHERE id_session = s.id AND s.id_teacher != id_questioner
        ORDER BY time_ask DESC LIMIT 1)
        AS user_id,
        (SELECT name FROM question JOIN users ON users.id = question.id_questioner 
        WHERE id_session = s.id AND s.id_teacher != id_questioner
        ORDER BY time_ask DESC LIMIT 1)
        AS user_name,
        (SELECT time_ask FROM question WHERE id_session = s.id AND s.id_teacher != id_questioner
        ORDER BY time_ask DESC LIMIT 1) AS time_ask
        FROM session s
        JOIN question q ON s.id = q.id_session
        WHERE s.id_teacher = {$iduser}
        GROUP BY s.id
        ORDER BY time_ask ASC";

        $results = $connect->query($sql);
        if(!$results){
            die($connect->error);
        }
    
        $data = array();
    
        if($results->num_rows > 0){
            while($row = $results->fetch_assoc()){
                $data[] = $row;
            }
        }
        echo json_encode($data);
    }
    
    if($type == "question"){
        $sql = "SELECT s.id AS s_id,s.name AS session_name, q.id AS q_id, q.id_questioner, q.notify,q.content,
        (SELECT id_commenter FROM comment 
        WHERE id_question = q.id AND q.id_questioner != id_commenter
        ORDER BY create_at DESC LIMIT 1) AS id_commenter,
        (SELECT name FROM users JOIN comment ON users.id = comment.id_commenter 
        WHERE id_question = q.id AND q.id_questioner != id_commenter
        ORDER BY create_at DESC LIMIT 1)
        AS user_name,
        -- (SELECT content FROM comment WHERE id_question = q.id ORDER BY create_at DESC LIMIT 1) AS c_content,
        (SELECT create_at FROM comment 
        WHERE id_question = q.id AND q.id_questioner != id_commenter
        ORDER BY create_at DESC LIMIT 1) AS time_ans
        FROM session s
        JOIN question q ON s.id = q.id_session
        JOIN comment c ON q.id = c.id_question
        WHERE q.id_questioner = {$iduser}
        GROUP BY q.id
        ORDER BY time_ans ASC";
        $results = $connect->query($sql);
        if(!$results){
            die($connect->error);
        }
    
        $data = array();
    
        if($results->num_rows > 0){
            while($row = $results->fetch_assoc()){
                $data[] = $row;
            }
        }
        echo json_encode($data);
    }
    if($type == "setsessionnotify"){
        $idsession = $_GET["id"];
        $sql = "UPDATE session SET notify = 0 WHERE id = {$idsession}";

        $result = $connect->query($sql);
        if(!$result){
            die($connect->error);
        }
    }
    if($type == "setquestionnotify"){
        $idquestion = $_GET["id"];
        $sql = "UPDATE question SET notify = 0 WHERE id = {$idquestion}";

        $result = $connect->query($sql);
        if(!$result){
            die($connect->error);
        }
    }
    
?>