import React from "react";
import style from "./shareURLModal.module.css";
import { useEffect } from "react";

// 카카오 공유하기 기능
const { Kakao } = window;

function ShareURLModal({ isOpen, onClose }) {
  const realUrl = "http://k9b205.p.ssafy.io";
  const localUrl = window.location.href;
  const SHARE_URL_KEY = process.env.REACT_APP_KAKAO_JAVASCRIPT_API_KEY;

  useEffect(() => {
    Kakao.cleanup();
    console.log(process.env.REACT_APP_KAKAO_JAVASCRIPT_API_KEY);
    Kakao.init(`${SHARE_URL_KEY}`);
    // 잘 적용됐으면 true 리턴
    console.log(Kakao.isInitialized());
  }, []);

  const shareKakao = () => {
    Kakao.Share.sendDefault({
      objectType: "feed",
      content: {
        title: "Wish Me, 나의 행운을 빌어줘",
        description: "수험생을 위한 응원 편지를 보내주세요 :)",
        imageUrl: "https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/login/clover.png",
        link: {
          mobileWebUrl: localUrl,
        },
      },
      buttons: [
        {
          title: "응원 편지 작성하기",
          link: {
            mobileWebUrl: localUrl,
          },
        },
      ],
    });
  };

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
        <div
          className={style.kakaoBtn}
          onClick={() => {
            shareKakao();
          }}
        >
          카카오톡 공유하기
        </div>
        <div className={style.LinkBtn}>링크 복사하기</div>
      </div>
    </div>
  );
}

export default ShareURLModal;
