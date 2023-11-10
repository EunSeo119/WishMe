import style from "./MainPopup.module.css";
import { IoIosArrowBack, IoIosArrowForward, IoIosClose } from "react-icons/io"; // IoIosArrowForward를 import
import { Link } from 'react-router-dom'

export default function MainPopup({ setShowMainPop }) {
    //props로 setShowMainPop을 받아서 사용
    
      const closePop = () => {
        localStorage.setItem("Visited", true);
        setShowMainPop(false);
      };
    
      return (
        <div className={style.Modalmodal}>
            <div className={style.Modalclose} onClick={closePop}>
                <IoIosClose />
            </div>
            <div className={style.Modaltitle}>Wish Me 나의 행운을 빌어줘</div>
            <div className={style.ModalContent}>
                학교 뿐만 아니라 나도 응원을 받을 수 있어요!
            </div>
            <div style={{width:"100%"}}>            <img className={style.ModalImg} src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/share/modal.jpg" crossOrigin="anonymous" />
</div>
            <div className={style.rowButton}>
                <Link className={style.Modalbtn} to={`/`} style={{textDecoration:"none"}}>
                    나도 응원받기
                </Link>
                <div
                    className={style.ModalbtnCancle}
                    onClick={closePop}
                >
                    닫기
                </div>
            </div>
            <div className={style.link}><a className={style.link} href="https://www.instagram.com/wish_me_1116/" target="_blank" rel="noopener noreferrer" >사용 방법 보러가기</a>
                </div>
        </div>      );
    }