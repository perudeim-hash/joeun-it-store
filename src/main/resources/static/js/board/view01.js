// 페이지 열릴 때 댓글 불러오기
window.onload = function () {
    loadComments();
};

function addComment() {

    const input = document.getElementById("commentInput");
    const text = input.value.trim();

    if (text === "") {
        alert("댓글을 입력하세요.");
        return;
    }

    const comments =
        JSON.parse(localStorage.getItem("comments")) || [];

    comments.unshift({
        content: text,
        date: new Date().toLocaleString()
    });

    localStorage.setItem("comments", JSON.stringify(comments));

    input.value = "";

    loadComments();
}

function loadComments() {

    const commentList =
        document.getElementById("commentList");

    commentList.innerHTML = "";

    const comments =
        JSON.parse(localStorage.getItem("comments")) || [];

    comments.forEach(comment => {

        const div = document.createElement("div");

        div.className = "comment-item";

        div.innerHTML = `
            <div class="comment-user">사용자</div>
            <div>${comment.content}</div>
            <div class="comment-date">${comment.date}</div>
        `;

        commentList.appendChild(div);
    });
}

function deletePost() {

    const check = confirm("정말 삭제하시겠습니까?");

    if (check) {
        alert("삭제되었습니다.");
        location.href = "/qna/list";
    }
}