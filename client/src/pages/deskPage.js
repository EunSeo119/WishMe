import style from './deskPage.module.css'

const DeskPage = () => {
  return (
    <div className={style.deskPage}>
      {/* <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/deskBackground.PNG" className={style.bg}/> */}
      <div className={style.board}>
        <div className={style.title}>
          <b>{}수능대박</b>님의 책상에
          <br></br>
          <b>{}246개</b>의 응원이 왔어요!
        </div>
      </div>
      <div className={style.desk}></div>
      <div className={style.btn}>
        <div className={style.cheerUpBtn}>응원하기</div>
        <div className={style.myDeskBtn}>내 책상 보기</div>
      </div>
    </div>
  )
}

export default DeskPage
