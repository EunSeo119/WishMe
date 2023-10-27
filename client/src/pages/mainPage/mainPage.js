import styleMain from "./mainPage.module.css"

const MainPage = () => {

    return (
        <div className={styleMain.bg}>
            <div className={styleMain.title}>
                Wish Me
                <img src="../../../public/assets/clover.png"></img>
            </div>
            <div className={styleMain.subTitle}>
                나의 행운을 빌어줘
            </div>
        </div>
    );
};

export default MainPage;
