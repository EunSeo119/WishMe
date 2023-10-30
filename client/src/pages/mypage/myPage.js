import React, { useState, useEffect } from "react";
import style from "./myPage.module.css";
import axios from "axios";

const MyPage  = () => {

    const [deskName, setDeskName] = useState("");
    const [schoolName, setSchoolName] = useState("");
    const [isEditing, setIsEditing] = useState(false);
    const [tempDeskName, setTempDeskName] = useState("");
    const [tempSchoolName, setTempSchoolName] = useState("");

    // 값 수정
    const changeNickname = (e) => {
        setTempDeskName(e.target.value);
    };
    const changeSchool = (e) => {
        setTempSchoolName(e.target.value);
    };

    // 수정하기 버튼 활성화
    const editClick = () => {
        setTempDeskName(deskName);
        setIsEditing(true);
    }

    const saveClick = () => {
        setIsEditing(false);

        const updatedData = {
            deskName: tempDeskName,
            schoolName: schoolName,
        };

        const AccessToken = localStorage.getItem("AccessToken");

        axios({
            method: "put",
            url: `http://localhost:8080/api/users/modify`,
            headers: {
              Authorization: `Bearer ${AccessToken}`,
            },
            data: updatedData,
        }).then((response) => {
            console.log("회원정보 수정: ", response);
        }).catch((error) => {
            console.error("API 요청 중 오류 발생:", error);
        });
    };

    useEffect(() => {
        const AccessToken = localStorage.getItem("AccessToken");
        axios({
          method: "get",
          url: `http://localhost:8080/api/users`,
          headers: {
            Authorization: `Bearer ${AccessToken}`,
          },
        })
          .then((response) => {
            const data = response.data;
            console.log(data);
            setDeskName(data.data.userNickname);
            setSchoolName(data.data.schoolName);
          })
          .catch((error) => {
            console.error("API 요청 중 오류 발생:", error);
          });
      }, []);

    return (
        <div className={style.body}>
            <div className={style.title}>
                <b>{deskName}님의<br/>
                마이페이지</b>
            </div>
            <div className={style.form}>
                <div className={style.nickname}>
                    <div>닉네임</div>
                        {isEditing ? (
                        <input type="text" value={tempDeskName} onChange={changeNickname} />
                        ) : (
                        <input type="text" value={deskName} style={{ color: 'gray', backgroundColor: '#edf4ef' }} readOnly />
                        )}
                        <div></div>
                </div>
                <div className={style.school}>
                    <div>학교</div>
                        {isEditing ? (
                        <>
                            <input type="text" value={schoolName} onChange={changeSchool} />
                            <div className={style.searchBtn}>검색</div>
                        </>
                        ) : (
                        <input type="text" value={schoolName} style={{ color: 'gray', backgroundColor: '#edf4ef', width: '80%', width: '240px'}} readOnly />
                        )}
                </div>
            </div>
            <div className={style.btn}>
                {isEditing ? (
                <div className={style.completeBtn} onClick={saveClick}>완료</div>
                ) : (
                <div className={style.modifyBtn} onClick={editClick}>수정하기</div>
            )}
            </div>
        </div>
    );
}

export default MyPage;