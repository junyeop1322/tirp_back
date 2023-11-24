<script setup>
import { ref, watch, onMounted } from "vue";

var map;
var newMarkers = [];
var newOverlays = [];
const markers = ref([]);
const overlays = ref([]);

// const props = defineProps({ presentList: Array, selectedMapItem: Object });
const props = defineProps({
  selectOne: Object,
  selectList: Array,
  isSetMarker: Boolean,
  isLogin: Boolean,
});
const emit = defineEmits(["getItem"]);
watch(
  () => props.selectOne,
  () => {
    console.log(props.selectOne);
    moveMap(props.selectOne);
  },
  { deep: true }
);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=4dbd35e206943ba5d329264fa4aad6ba&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

watch(
  () => props.isSetMarker,
  () => {
    if (props.isSetMarker) {
      newMarkers = [];
      newOverlays = [];
      console.log(props.selectList);
      props.selectList.map((item) => {
        newMarkers.push(
          new kakao.maps.Marker({
            position: new kakao.maps.LatLng(item.latitude, item.longitude),
          })
        );
      });
      reloadMarkersAndOverlays();
      moveMap(props.selectList[0]);
    }
  }
);

watch(
  () => props.selectList,
  () => {
    console.log(props.selectList);
    newMarkers = [];
    newOverlays = [];
    console.log(props.selectList);
    props.selectList.map((item) => {
      newMarkers.push(
        new kakao.maps.Marker({
          position: new kakao.maps.LatLng(item.latitude, item.longitude),
        })
      );
    });
    reloadMarkersAndOverlays();
    moveMap(props.selectList[0]);
  }
);

// map util

const initMap = () => {
  const container = document.getElementById("map");
  var options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  console.log(map);
};

const reloadMarkersAndOverlays = () => {
  var content =
    '<div class="wrap">' +
    '<div class="info">' +
    '<div class="title">' +
    "카카오 스페이스닷원" +
    '<div class="close" onclick="closeOverlay()" title="닫기"></div>' +
    "</div>" +
    '<div class="body">' +
    '<div class="img">' +
    '<img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
    "</div>" +
    '<div class="desc">' +
    '<div class="ellipsis">제주특별자치도 제주시 첨단로 242</div>' +
    '<div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' +
    '<div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +
    "</div>" +
    "</div>" +
    "</div>" +
    "</div>";
  // 기존에 표시된 마커들을 우선 지운다.
  markers.value.map((marker) => {
    overlays.value.map((overlay) => {
      closeOverlay(overlay);
    });

    marker.setMap(null);
  });

  var _overlays = [];
  newMarkers.forEach((marker, idx) => {
    const wrap = document.createElement("div");
    wrap.setAttribute("class", "wrap");
    const info = document.createElement("div"); // to Wrap
    info.setAttribute("class", "info");
    const title = document.createElement("div");
    title.setAttribute("class", "title");
    title.innerText = props.selectList[idx].title;
    const close = document.createElement("div");
    close.setAttribute("class", "close");
    close.addEventListener("click", () => {
      closeOverlay(overlays.value[idx]);
    });
    title.appendChild(close);
    info.appendChild(title);
    const body = document.createElement("div");
    body.setAttribute("class", "body");
    const imgDiv = document.createElement("div");
    imgDiv.setAttribute("class", "img");
    const img = document.createElement("img");
    img.setAttribute("src", props.selectList[idx].firstImage);
    img.setAttribute("width", "73");
    img.setAttribute("height", "70");
    imgDiv.appendChild(img);
    body.appendChild(imgDiv);
    const desc = document.createElement("div");
    desc.setAttribute("class", "desc");
    desc.innerText = props.selectList[idx].addr1;
    const getBtn = document.createElement("button");
    getBtn.addEventListener("click", () => {
      emit("getItem", props.selectList[idx]);
    });
    getBtn.innerHTML = "담기";
    desc.appendChild(getBtn);
    // const ellipsis = document.createElement("div");
    // ellipsis.setAttribute("class", "ellipsis");
    // ellipsis.innerText = props.selectList[idx].addr1;
    // desc.appendChild(ellipsis);
    // const jibun = document.createElement("div");
    // jibun.setAttribute("class", "ellipsis jibun");
    // jibun.innerText = "(우) 63309 (지번) 영평동 2181";
    //desc.appendChild(jibun);
    // const forLinkDiv = document.createElement("div");
    // const link = document.createElement("a");
    // link.setAttribute("href", "https://www.kakaocorp.com/main");
    // link.setAttribute("target", "_blank");
    // link.setAttribute("class", "link");
    // forLinkDiv.appendChild(link);
    // desc.appendChild(forLinkDiv);
    body.appendChild(desc);
    // info 에 body element 추가
    info.appendChild(body);
    wrap.appendChild(info);
    // overlay 하나 생성
    const overlay = new kakao.maps.CustomOverlay({
      content: wrap,
      map: map,
      position: marker.getPosition(),
    });

    _overlays.push(overlay);

    kakao.maps.event.addListener(marker, "click", function () {
      overlay.setMap(map);
    });
    marker.setMap(map);
  });
  overlays.value = _overlays;
  markers.value = newMarkers;
};

const moveMap = (loc) => {
  // 이동할 위도 경도 위치를 생성합니다
  // 지도 중심을 부드럽게 이동시킵니다
  // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
  var moveLatLon = new kakao.maps.LatLng(loc.latitude, loc.longitude);
  map.panTo(moveLatLon);
};

function closeOverlay(overlay) {
  overlay.setMap(null);
}
</script>

<template>
  <div id="map"></div>
</template>

<style setup>
#map {
  width: 100%;
  height: 100%;
}
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
  width: inherit;
  height: inherit;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
  width: 180px;
}
.desc .ellipsis {
  overflow: hidden;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
