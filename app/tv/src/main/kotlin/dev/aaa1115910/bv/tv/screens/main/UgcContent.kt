package dev.aaa1115910.bv.tv.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import dev.aaa1115910.bv.tv.component.TopNav
import dev.aaa1115910.bv.tv.component.UgcTopNavItem
import dev.aaa1115910.bv.tv.screens.main.ugc.AiContent
import dev.aaa1115910.bv.tv.screens.main.ugc.AnimalContent
import dev.aaa1115910.bv.tv.screens.main.ugc.CarContent
import dev.aaa1115910.bv.tv.screens.main.ugc.CinephileContent
import dev.aaa1115910.bv.tv.screens.main.ugc.DanceContent
import dev.aaa1115910.bv.tv.screens.main.ugc.DougaContent
import dev.aaa1115910.bv.tv.screens.main.ugc.EmotionContent
import dev.aaa1115910.bv.tv.screens.main.ugc.EntContent
import dev.aaa1115910.bv.tv.screens.main.ugc.FashionContent
import dev.aaa1115910.bv.tv.screens.main.ugc.FoodContent
import dev.aaa1115910.bv.tv.screens.main.ugc.GameContent
import dev.aaa1115910.bv.tv.screens.main.ugc.GymContent
import dev.aaa1115910.bv.tv.screens.main.ugc.HandmakeContent
import dev.aaa1115910.bv.tv.screens.main.ugc.HealthContent
import dev.aaa1115910.bv.tv.screens.main.ugc.HomeContent
import dev.aaa1115910.bv.tv.screens.main.ugc.InformationContent
import dev.aaa1115910.bv.tv.screens.main.ugc.KichikuContent
import dev.aaa1115910.bv.tv.screens.main.ugc.KnowledgeContent
import dev.aaa1115910.bv.tv.screens.main.ugc.LifeExperienceContent
import dev.aaa1115910.bv.tv.screens.main.ugc.LifeJoyContent
import dev.aaa1115910.bv.tv.screens.main.ugc.MusicContent
import dev.aaa1115910.bv.tv.screens.main.ugc.MysticismContent
import dev.aaa1115910.bv.tv.screens.main.ugc.OutdoorsContent
import dev.aaa1115910.bv.tv.screens.main.ugc.PaintingContent
import dev.aaa1115910.bv.tv.screens.main.ugc.ParentingContent
import dev.aaa1115910.bv.tv.screens.main.ugc.RuralContent
import dev.aaa1115910.bv.tv.screens.main.ugc.ShortPlayContent
import dev.aaa1115910.bv.tv.screens.main.ugc.SportsContent
import dev.aaa1115910.bv.tv.screens.main.ugc.TechContent
import dev.aaa1115910.bv.tv.screens.main.ugc.TravelContent
import dev.aaa1115910.bv.tv.screens.main.ugc.VlogContent
import dev.aaa1115910.bv.util.fInfo
import dev.aaa1115910.bv.util.requestFocus
import dev.aaa1115910.bv.viewmodel.ugc.UgcAiViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcAnimalViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcCarViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcCinephileViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcDanceViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcDougaViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcEmotionViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcEntViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcFashionViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcFoodViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcGameViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcGymViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcHandmakeViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcHealthViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcHomeViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcInformationViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcKichikuViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcKnowledgeViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcLifeExperienceViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcLifeJoyViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcMusicViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcMysticismViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcOutdoorsViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcPaintingViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcParentingViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcRuralViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcShortplayViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcSportsViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcTechViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcTravelViewModel
import dev.aaa1115910.bv.viewmodel.ugc.UgcVlogViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun UgcContent(
    modifier: Modifier = Modifier,
    navFocusRequester: FocusRequester,
    ugcDougaViewModel: UgcDougaViewModel = koinViewModel(),
    ugcGameViewModel: UgcGameViewModel = koinViewModel(),
    ugcKichikuViewModel: UgcKichikuViewModel = koinViewModel(),
    ugcMusicViewModel: UgcMusicViewModel = koinViewModel(),
    ugcDanceViewModel: UgcDanceViewModel = koinViewModel(),
    ugcCinephileViewModel: UgcCinephileViewModel = koinViewModel(),
    ugcEntViewModel: UgcEntViewModel = koinViewModel(),
    ugcKnowledgeViewModel: UgcKnowledgeViewModel = koinViewModel(),
    ugcTechViewModel: UgcTechViewModel = koinViewModel(),
    ugcInformationViewModel: UgcInformationViewModel = koinViewModel(),
    ugcFoodViewModel: UgcFoodViewModel = koinViewModel(),
    ugcShortplayViewModel: UgcShortplayViewModel = koinViewModel(),
    ugcCarViewModel: UgcCarViewModel = koinViewModel(),
    ugcFashionViewModel: UgcFashionViewModel = koinViewModel(),
    ugcSportsViewModel: UgcSportsViewModel = koinViewModel(),
    ugcAnimalViewModel: UgcAnimalViewModel = koinViewModel(),
    ugcVlogViewModel: UgcVlogViewModel = koinViewModel(),
    ugcPaintingViewModel: UgcPaintingViewModel = koinViewModel(),
    ugcAiViewModel: UgcAiViewModel = koinViewModel(),
    ugcHomeViewModel: UgcHomeViewModel = koinViewModel(),
    ugcOutdoorsViewModel: UgcOutdoorsViewModel = koinViewModel(),
    ugcGymViewModel: UgcGymViewModel = koinViewModel(),
    ugcHandmakeViewModel: UgcHandmakeViewModel = koinViewModel(),
    ugcTravelViewModel: UgcTravelViewModel = koinViewModel(),
    ugcRuralViewModel: UgcRuralViewModel = koinViewModel(),
    ugcParentingViewModel: UgcParentingViewModel = koinViewModel(),
    ugcHealthViewModel: UgcHealthViewModel = koinViewModel(),
    ugcEmotionViewModel: UgcEmotionViewModel = koinViewModel(),
    ugcLifeJoyViewModel: UgcLifeJoyViewModel = koinViewModel(),
    ugcLifeExperienceViewModel: UgcLifeExperienceViewModel = koinViewModel(),
    ugcMysticismViewModel: UgcMysticismViewModel = koinViewModel(),
) {
    val scope = rememberCoroutineScope()
    val logger = KotlinLogging.logger("UgcContent")

    val dougaState = rememberLazyListState()
    val gameState = rememberLazyListState()
    val kichikuState = rememberLazyListState()
    val musicState = rememberLazyListState()
    val danceState = rememberLazyListState()
    val cinephileState = rememberLazyListState()
    val entState = rememberLazyListState()
    val knowledgeState = rememberLazyListState()
    val techState = rememberLazyListState()
    val informationState = rememberLazyListState()
    val foodState = rememberLazyListState()
    val shortPlayState = rememberLazyListState()
    val carState = rememberLazyListState()
    val fashionState = rememberLazyListState()
    val sportsState = rememberLazyListState()
    val animalState = rememberLazyListState()
    val vlogState = rememberLazyListState()
    val paintingState = rememberLazyListState()
    val aiState = rememberLazyListState()
    val homeState = rememberLazyListState()
    val outdoorsState = rememberLazyListState()
    val gymState = rememberLazyListState()
    val handmakeState = rememberLazyListState()
    val travelState = rememberLazyListState()
    val ruralState = rememberLazyListState()
    val parentingState = rememberLazyListState()
    val healthState = rememberLazyListState()
    val emotionState = rememberLazyListState()
    val lifeJoyState = rememberLazyListState()
    val lifeExperienceState = rememberLazyListState()
    val mysticismState = rememberLazyListState()

    var selectedTab by remember { mutableStateOf(UgcTopNavItem.Douga) }
    var focusOnContent by remember { mutableStateOf(false) }

    //启动时刷新数据
    LaunchedEffect(Unit) {

    }

    BackHandler(focusOnContent) {
        logger.fInfo { "onFocusBackToNav" }
        navFocusRequester.requestFocus(scope)
        // scroll to top
        scope.launch(Dispatchers.Main) {
            when (selectedTab) {
                UgcTopNavItem.Douga -> dougaState.animateScrollToItem(0)
                UgcTopNavItem.Game -> gameState.animateScrollToItem(0)
                UgcTopNavItem.Kichiku -> kichikuState.animateScrollToItem(0)
                UgcTopNavItem.Music -> musicState.animateScrollToItem(0)
                UgcTopNavItem.Dance -> danceState.animateScrollToItem(0)
                UgcTopNavItem.Cinephile -> cinephileState.animateScrollToItem(0)
                UgcTopNavItem.Ent -> entState.animateScrollToItem(0)
                UgcTopNavItem.Knowledge -> knowledgeState.animateScrollToItem(0)
                UgcTopNavItem.Tech -> techState.animateScrollToItem(0)
                UgcTopNavItem.Information -> informationState.animateScrollToItem(0)
                UgcTopNavItem.Food -> foodState.animateScrollToItem(0)
                UgcTopNavItem.ShortPlay -> shortPlayState.animateScrollToItem(0)
                UgcTopNavItem.Car -> carState.animateScrollToItem(0)
                UgcTopNavItem.Fashion -> fashionState.animateScrollToItem(0)
                UgcTopNavItem.Sports -> sportsState.animateScrollToItem(0)
                UgcTopNavItem.Animal -> animalState.animateScrollToItem(0)
                UgcTopNavItem.Vlog -> vlogState.animateScrollToItem(0)
                UgcTopNavItem.Painting -> paintingState.animateScrollToItem(0)
                UgcTopNavItem.Ai -> aiState.animateScrollToItem(0)
                UgcTopNavItem.Home -> homeState.animateScrollToItem(0)
                UgcTopNavItem.Outdoors -> outdoorsState.animateScrollToItem(0)
                UgcTopNavItem.Gym -> gymState.animateScrollToItem(0)
                UgcTopNavItem.Handmake -> handmakeState.animateScrollToItem(0)
                UgcTopNavItem.Travel -> travelState.animateScrollToItem(0)
                UgcTopNavItem.Rural -> ruralState.animateScrollToItem(0)
                UgcTopNavItem.Parenting -> parentingState.animateScrollToItem(0)
                UgcTopNavItem.Health -> healthState.animateScrollToItem(0)
                UgcTopNavItem.Emotion -> emotionState.animateScrollToItem(0)
                UgcTopNavItem.LifeJoy -> lifeJoyState.animateScrollToItem(0)
                UgcTopNavItem.LifeExperience -> lifeExperienceState.animateScrollToItem(0)
                UgcTopNavItem.Mysticism -> mysticismState.animateScrollToItem(0)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopNav(
                modifier = Modifier
                    .focusRequester(navFocusRequester),
                items = UgcTopNavItem.entries,
                isLargePadding = !focusOnContent,
                onSelectedChanged = { nav ->
                    selectedTab = nav as UgcTopNavItem
                },
                onClick = { nav ->
                    when (nav) {
                        UgcTopNavItem.Douga -> ugcDougaViewModel.reloadAll()
                        UgcTopNavItem.Game -> ugcGameViewModel.reloadAll()
                        UgcTopNavItem.Kichiku -> ugcKichikuViewModel.reloadAll()
                        UgcTopNavItem.Music -> ugcMusicViewModel.reloadAll()
                        UgcTopNavItem.Dance -> ugcDanceViewModel.reloadAll()
                        UgcTopNavItem.Cinephile -> ugcCinephileViewModel.reloadAll()
                        UgcTopNavItem.Ent -> ugcEntViewModel.reloadAll()
                        UgcTopNavItem.Knowledge -> ugcKnowledgeViewModel.reloadAll()
                        UgcTopNavItem.Tech -> ugcTechViewModel.reloadAll()
                        UgcTopNavItem.Information -> ugcInformationViewModel.reloadAll()
                        UgcTopNavItem.Food -> ugcFoodViewModel.reloadAll()
                        UgcTopNavItem.ShortPlay -> ugcShortplayViewModel.reloadAll()
                        UgcTopNavItem.Car -> ugcCarViewModel.reloadAll()
                        UgcTopNavItem.Fashion -> ugcFashionViewModel.reloadAll()
                        UgcTopNavItem.Sports -> ugcSportsViewModel.reloadAll()
                        UgcTopNavItem.Animal -> ugcAnimalViewModel.reloadAll()
                        UgcTopNavItem.Vlog -> ugcVlogViewModel.reloadAll()
                        UgcTopNavItem.Painting -> ugcPaintingViewModel.reloadAll()
                        UgcTopNavItem.Ai -> ugcAiViewModel.reloadAll()
                        UgcTopNavItem.Home -> ugcHomeViewModel.reloadAll()
                        UgcTopNavItem.Outdoors -> ugcOutdoorsViewModel.reloadAll()
                        UgcTopNavItem.Gym -> ugcGymViewModel.reloadAll()
                        UgcTopNavItem.Handmake -> ugcHandmakeViewModel.reloadAll()
                        UgcTopNavItem.Travel -> ugcTravelViewModel.reloadAll()
                        UgcTopNavItem.Rural -> ugcRuralViewModel.reloadAll()
                        UgcTopNavItem.Parenting -> ugcParentingViewModel.reloadAll()
                        UgcTopNavItem.Health -> ugcHealthViewModel.reloadAll()
                        UgcTopNavItem.Emotion -> ugcEmotionViewModel.reloadAll()
                        UgcTopNavItem.LifeJoy -> ugcLifeJoyViewModel.reloadAll()
                        UgcTopNavItem.LifeExperience -> ugcLifeExperienceViewModel.reloadAll()
                        UgcTopNavItem.Mysticism -> ugcMysticismViewModel.reloadAll()
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .onFocusChanged { focusOnContent = it.hasFocus }
        ) {
            AnimatedContent(
                targetState = selectedTab,
                label = "ugc animated content",
                transitionSpec = {
                    val coefficient = 10
                    if (targetState.ordinal < initialState.ordinal) {
                        fadeIn() + slideInHorizontally { -it / coefficient } togetherWith
                                fadeOut() + slideOutHorizontally { it / coefficient }
                    } else {
                        fadeIn() + slideInHorizontally { it / coefficient } togetherWith
                                fadeOut() + slideOutHorizontally { -it / coefficient }
                    }
                }
            ) { screen ->
                when (screen) {
                    UgcTopNavItem.Douga -> DougaContent(lazyListState = dougaState)
                    UgcTopNavItem.Game -> GameContent(lazyListState = gameState)
                    UgcTopNavItem.Kichiku -> KichikuContent(lazyListState = kichikuState)
                    UgcTopNavItem.Music -> MusicContent(lazyListState = musicState)
                    UgcTopNavItem.Dance -> DanceContent(lazyListState = danceState)
                    UgcTopNavItem.Cinephile -> CinephileContent(lazyListState = cinephileState)
                    UgcTopNavItem.Ent -> EntContent(lazyListState = entState)
                    UgcTopNavItem.Knowledge -> KnowledgeContent(lazyListState = knowledgeState)
                    UgcTopNavItem.Tech -> TechContent(lazyListState = techState)
                    UgcTopNavItem.Information -> InformationContent(lazyListState = informationState)
                    UgcTopNavItem.Food -> FoodContent(lazyListState = foodState)
                    UgcTopNavItem.ShortPlay -> ShortPlayContent(lazyListState = shortPlayState)
                    UgcTopNavItem.Car -> CarContent(lazyListState = carState)
                    UgcTopNavItem.Fashion -> FashionContent(lazyListState = fashionState)
                    UgcTopNavItem.Sports -> SportsContent(lazyListState = sportsState)
                    UgcTopNavItem.Animal -> AnimalContent(lazyListState = animalState)
                    UgcTopNavItem.Vlog -> VlogContent(lazyListState = vlogState)
                    UgcTopNavItem.Painting -> PaintingContent(lazyListState = paintingState)
                    UgcTopNavItem.Ai -> AiContent(lazyListState = aiState)
                    UgcTopNavItem.Home -> HomeContent(lazyListState = homeState)
                    UgcTopNavItem.Outdoors -> OutdoorsContent(lazyListState = outdoorsState)
                    UgcTopNavItem.Gym -> GymContent(lazyListState = gymState)
                    UgcTopNavItem.Handmake -> HandmakeContent(lazyListState = handmakeState)
                    UgcTopNavItem.Travel -> TravelContent(lazyListState = travelState)
                    UgcTopNavItem.Rural -> RuralContent(lazyListState = ruralState)
                    UgcTopNavItem.Parenting -> ParentingContent(lazyListState = parentingState)
                    UgcTopNavItem.Health -> HealthContent(lazyListState = healthState)
                    UgcTopNavItem.Emotion -> EmotionContent(lazyListState = emotionState)
                    UgcTopNavItem.LifeJoy -> LifeJoyContent(lazyListState = lifeJoyState)
                    UgcTopNavItem.LifeExperience -> LifeExperienceContent(lazyListState = lifeExperienceState)
                    UgcTopNavItem.Mysticism -> MysticismContent(lazyListState = mysticismState)
                }
            }
        }
    }
}