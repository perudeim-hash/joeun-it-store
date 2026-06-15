function goList() {
    location.href = "/qna/list";
}

function goUpdate(id) {
    location.href = "/qna/update?id=" + id;
}

function deletePost() {
    const check = confirm("정말 삭제하시겠습니까?");

    if (check) {
        alert("삭제되었습니다.");
        location.href = "/qna/list";
    }
}