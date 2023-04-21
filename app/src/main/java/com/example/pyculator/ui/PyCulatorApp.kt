package com.example.pyculator.ui

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.pyculator.R
import com.example.pyculator.ui.pages.*
import com.example.pyculator.ui.theme.PyculatorTheme
import com.example.pyculator.viewmodels.MemoryViewModel
import com.example.pyculator.viewmodels.SettingsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PyCulatorApp(
    memoryViewModel: MemoryViewModel,
    staticResult: String? = null, // Preview can't calculate result
    context: Context,
) {
    val settingsViewModel = SettingsViewModel(context)
    val theme = settingsViewModel.themeFlow.collectAsState(if (isSystemInDarkTheme()) "dark" else "light")
    val codeFontSize = settingsViewModel.codeFontSizeFlow.collectAsState(16)

    val filesDir = context.filesDir

    val keyboard = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val memoryList by memoryViewModel.memoryList.collectAsState()
    var toEval by remember { mutableStateOf("") }

    val pagerState = rememberPagerState(1)
    val scaffoldState = rememberScaffoldState()
    val pageNames = listOf(
        stringResource(R.string.Favorites),
        stringResource(R.string.Expression),
        "SymPy",
        stringResource(R.string.Code),
        stringResource(R.string.Settings),
    )
    val pageIcons = listOf(
        painterResource(R.drawable.baseline_favorite_24),
        painterResource(R.drawable.baseline_create_24),
        painterResource(R.drawable.baseline_sentiment_neutral_24),
        painterResource(R.drawable.baseline_code_24),
        painterResource(R.drawable.baseline_settings_24),
    )

    // Hide keyboard on page change
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            keyboard?.hide()
        }
    }

    PyculatorTheme(theme.value) {
        // HorizontalPager with BottomAppBar
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigation {
                    for (pageNumber in pageNames.indices) {
                        BottomNavigationItem(
                            icon = {
                                Icon(pageIcons[pageNumber], null)
                            },
                            selected = pageNumber == pagerState.currentPage,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pageNumber)
                                }
                            },
                            label = { Text(pageNames[pageNumber]) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            HorizontalPager(
                contentPadding = innerPadding,
                count = 5,
                state = pagerState,
            ) { pageNumber ->
                when (pageNumber) {
                    0 -> FavoritesPage(
                        memoryList = memoryList,
                        onPaste = { toEval += it },
                    )
                    1 -> ExpressionPage(
                        memoryList = memoryList,
                        toEval = toEval,
                        onToEvalChange = { toEval = it },
                        staticResult = staticResult,
                    )
                    2 -> SymPyPage(
                        coroutineScope = coroutineScope,
                        memoryList = memoryList,
                    )
                    3 -> CodePage(
                        context = context,
                        filesDir = filesDir,
                        codeFontSize = codeFontSize.value,
                    )
                    4 -> OptionsPage(
                        settingsViewModel = settingsViewModel,
                        codeFontSize = codeFontSize.value,
                    )
                }
            }
        }
    }
}