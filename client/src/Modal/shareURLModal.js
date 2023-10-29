import React from "react";
import style from "./shareURLModal.module.css";

function ShareURLModal({ isOpen, onClose }) {
  if (!isOpen) return null;

  return (
    <div className={style.modal}>
      {/* <div className={style.header}> */}
      <div className={style.close} onClick={onClose}>
        X
      </div>
      <div className={style.title}>공유하기</div>
      {/* </div> */}
      <div className={style.btn}>
        <div className={style.kakaoBtn}>카카오톡 공유하기</div>
        <div className={style.LinkBtn}>링크 복사하기</div>
      </div>
    </div>
  );
}

export default ShareURLModal;
