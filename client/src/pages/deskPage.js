import style from "./deskPage.module.css";
import styleApp from '../app.module.css'
import { Link } from "react-router-dom";

const DeskPage = () => {

    return (
        <div className={styleApp.app}>
            <div className={style.deskPage}>
                <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/deskBackground.PNG" className={styleApp.bg} />
                <div className={style.board}>
                    <div className={style.title}><b>{ }수능대박</b>님의 책상에<br /><b>{ }246개</b>의 응원이 왔어요!</div>
                </div>
                <div className={style.desk}>

                </div>
                <div className={style.btn}>
                    <Link to="/desk/write" className={style.link}>
                        <div className={style.cheerUpBtn}>응원하기</div>
                    </Link>
                    <div className={style.myDeskBtn}>내 책상 보기</div>
                </div>
            </div>
        </div>
    );
};

export default DeskPage;
